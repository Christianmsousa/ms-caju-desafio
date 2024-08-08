package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Wallet balance domain test")
class WalletBalanceTest {

    @Nested
    @DisplayName("Cannot debit balance test")
    class CannotDebitBalanceTest {
        @Test
        @DisplayName("When attempting to debit an amount greater than the available balance, then should return true")
        void whenRequestToDebitAmountGreaterThanAvailableBalanceThenShouldReturnTrue() {
            var amountToDebit = new BigDecimal(30);
            var walletBalance = new WalletBalance(1L , BalanceType.CASH, new BigDecimal(10), 2L);

            var result = walletBalance.cannotDebit(amountToDebit);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("When attempting to debit an amount less than the available balance, then should return false")
        void whenRequestToDebitAmountLessThanAvailableBalanceThenShouldReturnFalse() {
            var amountToDebit = new BigDecimal(30);
            var walletBalance = new WalletBalance(1L , BalanceType.CASH, new BigDecimal(50), 2L);

            var result = walletBalance.cannotDebit(amountToDebit);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("When attempting to debit an amount is equal the available balance, then should return false")
        void whenRequestToDebitAmountIsEqualTheAvailableBalanceThenShouldReturnFalse() {
            var amountToDebit = new BigDecimal(50);
            var walletBalance = new WalletBalance(1L , BalanceType.CASH, new BigDecimal(50), 2L);

            var result = walletBalance.cannotDebit(amountToDebit);

            assertThat(result).isFalse();
        }
    }

}