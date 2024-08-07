package com.caju_desafio.ms_caju_desafio.core.gateway;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletGateway {
    Optional<Wallet> findByAccountId(String accountId);
    Wallet persist(Wallet wallet);
    boolean debit(Long walletBalanceId, BigDecimal balance, Long version);
}
