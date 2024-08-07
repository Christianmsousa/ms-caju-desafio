package com.caju_desafio.ms_caju_desafio.entrypoint.payload.request;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.WalletBalance;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletBalanceRequest(

        @NotNull(message = "Balance type cannot be null")
        @JsonProperty("balance_type")
        BalanceTypeRequest balanceType,

        @NotNull(message = "Balance cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be zero or positive")
        BigDecimal balance) {


    public WalletBalance toWalletBalance() {
        return new WalletBalance(null, balanceType.mapToWalletBalanceType(), balance, null);
    }
}
