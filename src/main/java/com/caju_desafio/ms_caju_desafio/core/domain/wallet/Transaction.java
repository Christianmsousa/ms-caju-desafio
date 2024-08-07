package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import java.math.BigDecimal;

public record Transaction(
    String account,
    BigDecimal totalAmount,
    Long mcc,
    String merchant
) { }
