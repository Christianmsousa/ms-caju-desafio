package com.caju_desafio.ms_caju_desafio.entrypoint.exception;


import com.caju_desafio.ms_caju_desafio.core.exception.ApiError;
import com.caju_desafio.ms_caju_desafio.core.exception.MccException;
import com.caju_desafio.ms_caju_desafio.core.exception.MerchantException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiError.ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return ApiError.ErrorDetail.builder()
                            .field(fieldName)
                            .message(errorMessage)
                            .build();
                })
                .toList();

        ApiError apiError = ApiError.createApiError("Validation failed", HttpStatus.BAD_REQUEST.value(), errorDetails);
        return ResponseEntity.badRequest().body(apiError);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<ApiError.ErrorDetail> errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String fieldName = violation.getPropertyPath().toString();
                    String errorMessage = violation.getMessage();
                    return ApiError.ErrorDetail.builder()
                            .field(fieldName)
                            .message(errorMessage)
                            .build();
                })
                .toList();

        ApiError apiError = ApiError.createApiError("Constraint violation", HttpStatus.BAD_REQUEST.value(), errorDetails);
        return ResponseEntity.badRequest().body(apiError);
    }


    @ExceptionHandler(value = MccException.class)
    protected ResponseEntity<ApiError> mccExceptionHandler(final MccException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(exception.getError());
    }

    @ExceptionHandler(value = MerchantException.class)
    protected ResponseEntity<ApiError> merchantExceptionHandler(final MerchantException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(exception.getError());
    }
    private static int getStatusByCode(Integer exceptionStatusCode) {
        return (nonNull(exceptionStatusCode) ? exceptionStatusCode : INTERNAL_SERVER_ERROR.value());
    }
}
