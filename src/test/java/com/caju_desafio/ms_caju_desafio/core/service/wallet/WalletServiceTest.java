package com.caju_desafio.ms_caju_desafio.core.service.wallet;

import com.caju_desafio.ms_caju_desafio.PostgreSqlContainerConfig;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.BalanceType;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Transaction;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.TransactionCode;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.WalletBalance;
import com.caju_desafio.ms_caju_desafio.persistence.repository.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(
        PostgreSqlContainerConfig.class
)
@SpringBootTest
class WalletServiceTest {

    private static final String ACCOUNT_ID = "32";

    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    void init() {
        var walletBalanceCash = new WalletBalance(null, BalanceType.CASH, new BigDecimal(60), 2L);
        var walletBalanceFood = new WalletBalance(null, BalanceType.FOOD, new BigDecimal(60), 1L);
        Set<WalletBalance> balances = Set.of(walletBalanceFood, walletBalanceCash);

        var wallet = new Wallet(null, ACCOUNT_ID, Wallet.calculateTotalAmount(balances) , balances);
        walletService.createWallet(wallet);
    }

    @AfterEach
    void tearDown() {
        walletRepository.deleteAll();
    }

    @Test
    @DisplayName("When parallel transactions are processed and there is enough balance, then they should retry if needed and return successfully")
    void whenHasParallelRequestAndHasAmountToDebitThenRetryAndReturnSuccessfully() throws Exception {
        var expectedBalance = new BigDecimal(95).setScale(2);
        var transaction1 = new Transaction(ACCOUNT_ID, new BigDecimal(10), 5411L, "SUPERMERCADO");
        var transaction2 = new Transaction(ACCOUNT_ID, new BigDecimal(15), 5411L, "LOJA");

        // When
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> walletService.transaction(transaction1));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> walletService.transaction(transaction2));
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);
        allOf.join();
        String result1 = future1.get();
        String result2 = future2.get();

        var walletAfterTransactions = walletService.getWalletByAccountId(ACCOUNT_ID).get();
        assertThat(walletAfterTransactions.totalAmount()).isEqualTo(expectedBalance);
        assertThat(result1).isEqualTo(TransactionCode.SUCCESS.getCode());
        assertThat(result2).isEqualTo(TransactionCode.SUCCESS.getCode());
    }


    @Test
    @DisplayName("When concurrent transactions with identical amounts are processed, then only one should be successfully completed and the other rejected due to insufficient balance")
    void whenConcurrentTransactionsWithIdenticalAmountsThenOnlyOneShouldBeSuccessfullyCompletedAndTheOtherRejected() throws Exception {
        var expectedBalance = new BigDecimal(60).setScale(2);
        var transaction1 = new Transaction(ACCOUNT_ID, new BigDecimal(60), 5411L, "SUPERMERCADO");
        var transaction2 = new Transaction(ACCOUNT_ID, new BigDecimal(60), 5411L, "LOJA");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> walletService.transaction(transaction1));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> walletService.transaction(transaction2));
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);
        allOf.join();
        String result1 = future1.get();
        String result2 = future2.get();

        var walletAfterTransactions = walletService.getWalletByAccountId(ACCOUNT_ID).get();
        assertThat(walletAfterTransactions.totalAmount()).isEqualTo(expectedBalance);
        var results = List.of(result2, result1);
        assertThat(results).contains(TransactionCode.SUCCESS.getCode());
        assertThat(results).contains(TransactionCode.REJECTED.getCode());
    }


}