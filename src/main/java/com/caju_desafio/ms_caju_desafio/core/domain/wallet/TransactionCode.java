package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionCode {
    SUCCESS("00"), REJECTED("51"), ERROR("07");

    private final String code;
}
