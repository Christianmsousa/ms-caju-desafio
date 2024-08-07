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
        return balances.stream().filter(walletBalance -> walletBalance.balanceType().equals(balanceType)).findFirst();
    }
    public boolean hasValueToDebit(BalanceType balanceType, BigDecimal amount) {
        if (hasTotalValueToDebit(amount)) return false;

        return balances.stream()
                        .filter((balance) -> balance.balanceType().equals(balanceType))
                        .map(WalletBalance::balance)
                        .allMatch(balance -> balance.compareTo(amount) >= 0);
    }

    public boolean hasTotalValueToDebit(BigDecimal amount) {
        return amount.compareTo(totalAmount) < 0;
    }

}
