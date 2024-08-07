package com.caju_desafio.ms_caju_desafio.core.domain.merchant;


import java.time.LocalDateTime;

public record Merchant(
    Long id,
    String name,
    Long mcc,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) { }
