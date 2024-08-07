package com.caju_desafio.ms_caju_desafio.entrypoint.payload.request;


import jakarta.validation.constraints.NotNull;

public record MerchantCreateRequest(@NotNull String name, Long mcc) {
}
