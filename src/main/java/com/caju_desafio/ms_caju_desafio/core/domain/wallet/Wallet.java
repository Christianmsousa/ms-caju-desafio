package com.caju_desafio.ms_caju_desafio.core.domain.wallet;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;


public record Wallet(
    Long id,
    String accountId,
    BigDecimal totalAmount,
    Set<WalletBalance> balances
) {

    public static BigDecimal calculateTotalAmount(Set<WalletBalance> balanceList) {
        return balanceList.stream()
                          .map(WalletBalance::balance)
                          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<WalletBalance> getWalletBalanceByType(BalanceType balanceType) {
        return balances.stream()
                        .filter(walletBalance -> walletBalance.balanceType().equals(balanceType))
                        .findFirst();
    }

    public boolean cannotDebit(BigDecimal amount) {
        return totalAmount.compareTo(amount) < 0;
    }

}
