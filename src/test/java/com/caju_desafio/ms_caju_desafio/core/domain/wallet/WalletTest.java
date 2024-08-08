package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet.calculateTotalAmount;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Wallet domain test")
class WalletTest {

    private static final String ACCOUNT_ID = "4033";
    private static final Long WALLET_ID = 14L;

    @Nested
    @DisplayName("Cannot debit balance feature test")
    class CannotDebitBalanceTest {
        @Test
        @DisplayName("When attempting to debit an amount greater than the available balance, then should return true")
        void whenRequestToDebitAmountGreaterThanAvailableBalanceThenShouldReturnTrue() {
            var amountToDebit = new BigDecimal(300);
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(10), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(50), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.cannotDebit(amountToDebit);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("When attempting to debit an amount less than the available balance, then should return false")
        void whenRequestToDebitAmountLessThanAvailableBalanceThenShouldReturnFalse() {
            var amountToDebit = new BigDecimal(55);
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(10), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(50), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.cannotDebit(amountToDebit);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("When attempting to debit an amount is equal the available balance, then should return false")
        void whenRequestToDebitAmountIsEqualTheAvailableBalanceThenShouldReturnFalse() {
            var amountToDebit = new BigDecimal(60);
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(10), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(50), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.cannotDebit(amountToDebit);

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Calculate total amount feature test")
    class CalculateTotalAmountTest {

        @Test
        @DisplayName("When request to calculate total amount of balances, then return expected value")
        void whenRequestToCalculateTotalAmountOfBalancesThenReturnExpectedValue() {
            var expectedValue = new BigDecimal(60);
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(10), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(50), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.totalAmount();

            assertThat(result).isEqualTo(expectedValue);
        }


        @Test
        @DisplayName("When request to calculate total amount of balances and balances is zero, then return zero")
        void whenRequestToCalculateTotalAmountOfBalancesAndBalancesIsZeroThenReturnZero() {
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(0), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(0), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.totalAmount();

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("When request to calculate total amount of balances and has no balance, then return zero")
        void whenRequestToCalculateTotalAmountAndHasNoBalancesThenReturnZero() {
            Set<WalletBalance> balances = Set.of();
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.totalAmount();

            assertThat(result).isZero();
        }
    }


    @Nested
    @DisplayName("Get wallet balance by type feature test")
    class GetWalletByBalanceTypeTest {

        @Test
        @DisplayName("When request wallet balance by type and wallet type is food, then return food")
        void whenRequestWalletBalanceByTypeAndWalletTypeIsFoodThenReturn() {
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(0), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(0), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.getWalletBalanceByType(BalanceType.FOOD);

            assertThat(result).isNotEmpty();
            assertThat(result.get()).isEqualTo(walletBalanceFood);
        }

        @Test
        @DisplayName("When request wallet balance by type and wallet type is cash, then return food")
        void whenRequestWalletBalanceByTypeAndWalletTypeIsCashThenReturn() {
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(0), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(0), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.getWalletBalanceByType(BalanceType.CASH);

            assertThat(result).isNotEmpty();
            assertThat(result.get()).isEqualTo(walletBalanceCash);
        }

        @Test
        @DisplayName("When request wallet balance by type and user has no wallet mapped, then return food")
        void whenRequestWalletBalanceByTypeAndUserHasNoWalletMappedThenReturnEmpty() {
            var walletBalanceCash = new WalletBalance(3L , BalanceType.CASH, new BigDecimal(0), 2L);
            var walletBalanceFood = new WalletBalance(2L , BalanceType.FOOD, new BigDecimal(0), 1L);
            Set<WalletBalance> balances = Set.of(walletBalanceFood,walletBalanceCash);
            var wallet = new Wallet(WALLET_ID, ACCOUNT_ID, calculateTotalAmount(balances), balances);

            var result = wallet.getWalletBalanceByType(BalanceType.MEAL);

            assertThat(result).isEmpty();
        }

    }
}