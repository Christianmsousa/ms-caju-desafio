package com.caju_desafio.ms_caju_desafio.core.exception;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    @JsonIgnore
    private int status;
    private String code;
    private String description;
    private List<ErrorDetail> errors;

    public static ApiError createApiError(String description, int status) {
        return ApiError.builder()
                .description(description)
                .status(status)
                .build();
    }

    public static ApiError createApiError(String description, int status, List<ErrorDetail> errors) {
        return ApiError.builder()
                .description(description)
                .status(status)
                .errors(errors)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {
        private String field;
        private String message;
    }

}
