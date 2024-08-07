package com.caju_desafio.ms_caju_desafio.core.exception;

import lombok.Getter;

@Getter
public class MerchantException extends RuntimeException {

    private final ApiError error;

    public MerchantException(final ApiError error) {
        this.error = error;
    }

}
