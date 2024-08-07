package com.caju_desafio.ms_caju_desafio.core.exception;

import lombok.Getter;

@Getter
public class WalletException extends RuntimeException {

    private final ApiError error;

    public WalletException(final ApiError error) {
        this.error = error;
    }

}
