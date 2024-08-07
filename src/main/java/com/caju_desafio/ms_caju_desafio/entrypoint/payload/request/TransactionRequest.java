package com.caju_desafio.ms_caju_desafio.entrypoint.payload.request;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Transaction;

import java.math.BigDecimal;

public record TransactionRequest(
        String account,
        BigDecimal totalAmount,
        Long mcc,
        String merchant
) {
    public Transaction toTransaction() {
        return new Transaction(account, totalAmount, mcc, merchant);
    }
}
