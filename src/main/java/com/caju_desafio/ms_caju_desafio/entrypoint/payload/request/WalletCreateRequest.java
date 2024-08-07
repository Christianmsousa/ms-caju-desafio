package com.caju_desafio.ms_caju_desafio.entrypoint.payload.request;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.stream.Collectors;

public record WalletCreateRequest(
        @JsonProperty("account_id")
        @NotBlank(message = "Account ID cannot be empty")
        String accountId,

        @NotNull(message = "Balances cannot be null")
        @Size(min = 1, max = 3, message = "The number of wallet balances must be between 1 and 3.")
        Set<@Valid WalletBalanceRequest> balances
) {

    public Wallet toWallet() {
        var balancesConverted = balances.stream()
                .map(WalletBalanceRequest::toWalletBalance)
                .collect(Collectors.toSet());

        return new Wallet(null, accountId, null, balancesConverted);
    }
}
