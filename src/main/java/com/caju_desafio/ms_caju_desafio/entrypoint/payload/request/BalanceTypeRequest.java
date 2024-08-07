package com.caju_desafio.ms_caju_desafio.entrypoint.payload.request;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.BalanceType;

public enum BalanceTypeRequest {
    FOOD, MEAL, CASH;

    public BalanceType mapToWalletBalanceType() {
        return switch (this) {
            case FOOD -> BalanceType.FOOD;
            case MEAL -> BalanceType.MEAL;
            case CASH -> BalanceType.CASH;
        };
    }
}
