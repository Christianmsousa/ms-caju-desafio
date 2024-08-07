package com.caju_desafio.ms_caju_desafio.core.service.wallet;


import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.BalanceType;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Transaction;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.TransactionCode;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.caju_desafio.ms_caju_desafio.core.exception.ConcurrencyException;
import com.caju_desafio.ms_caju_desafio.core.exception.WalletException;
import com.caju_desafio.ms_caju_desafio.core.gateway.MerchantGateway;
import com.caju_desafio.ms_caju_desafio.core.gateway.WalletGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.caju_desafio.ms_caju_desafio.core.exception.ApiError.createApiError;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletGateway walletGateway;
    private final MerchantGateway merchantGateway;

    public Wallet createWallet(Wallet walletRequest) {
        walletGateway.findByAccountId(walletRequest.accountId()).ifPresent(e -> {
            throw new WalletException(createApiError("Wallet already registered", CONFLICT.value()));
        });

        return walletGateway.persist(walletRequest);
    }

    public Optional<Wallet> getWalletByAccountId(String accountId) {
        return walletGateway.findByAccountId(accountId);
    }

    @Retryable(retryFor = ConcurrencyException.class, maxAttempts = 2)
    public String transaction(Transaction transaction) {
        var walletOptional = walletGateway.findByAccountId(transaction.account());

        if(walletOptional.isEmpty()){
            return TransactionCode.ERROR.getCode();
        }
        var wallet = walletOptional.get();

        if(!wallet.hasTotalValueToDebit(transaction.totalAmount())) {
            return TransactionCode.REJECTED.getCode();
        }
        var merchant = merchantGateway.findByName(transaction.merchant().toUpperCase());

        var mcc = merchant.map(Merchant::mcc).orElse(transaction.mcc());
        var balancetype = BalanceType.getByMcc(mcc);


        return wallet
                .getWalletBalanceByType(balancetype)
                .map(walletBalance -> {
                    var valueToSet = walletBalance.balance().subtract(transaction.totalAmount());
                    walletGateway.debit(walletBalance.id(), valueToSet, walletBalance.version());
                    return TransactionCode.SUCCESS.getCode();
                })
                .orElse(TransactionCode.ERROR.getCode());
    }

}
