package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import java.math.BigDecimal;

public record WalletBalance(
    Long id,
    BalanceType balanceType,
    BigDecimal balance,
    Long version
) {

    public boolean cannotDebit(BigDecimal amountToDebit) {
        return balance.compareTo(amountToDebit) < 0;
    }
}
