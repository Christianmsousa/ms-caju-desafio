package com.caju_desafio.ms_caju_desafio.core.exception;

import lombok.Getter;

@Getter
public class MccException extends RuntimeException {

    private final ApiError error;

    public MccException(final ApiError error) {
        this.error = error;
    }

}
