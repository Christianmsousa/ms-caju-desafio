package com.caju_desafio.ms_caju_desafio.core.domain.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Getter
@AllArgsConstructor
public enum BalanceType {
    FOOD(List.of(5411L, 5412L)),
    MEAL(List.of(5811L, 5812L)),
    CASH(emptyList());

    private final List<Long> mcc;

    public static BalanceType getByMcc(Long mcc) {
        return Arrays.stream(values())
                .filter(value -> value.getMcc().contains(mcc))
                .findFirst()
                .orElse(CASH);
    }
}
