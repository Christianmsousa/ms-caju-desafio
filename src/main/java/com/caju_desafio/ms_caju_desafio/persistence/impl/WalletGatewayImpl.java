package com.caju_desafio.ms_caju_desafio.persistence.impl;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.caju_desafio.ms_caju_desafio.core.exception.ConcurrencyException;
import com.caju_desafio.ms_caju_desafio.core.gateway.WalletGateway;
import com.caju_desafio.ms_caju_desafio.persistence.entity.wallet.WalletEntity;
import com.caju_desafio.ms_caju_desafio.persistence.repository.WalletBalanceRepository;
import com.caju_desafio.ms_caju_desafio.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletGatewayImpl implements WalletGateway {

    private final WalletRepository walletRepository;
    private final WalletBalanceRepository walletBalanceRepository;

    @Override
    public Optional<Wallet> findByAccountId(String accountId) {
        return walletRepository.findByAccountId(accountId).map(WalletEntity::toWallet);
    }

    @Override
    public Wallet persist(Wallet wallet) {
        return walletRepository.save(new WalletEntity(wallet)).toWallet();
    }

    @Override
    public boolean debit(Long walletBalanceId, BigDecimal balance, Long version) throws ConcurrencyException {

        int updatedCount = walletBalanceRepository.updateBalanceByIdAndVersion(walletBalanceId, balance, version);
        if (updatedCount == 0) {
            throw new ConcurrencyException("Update failed due to concurrent modification.");
        }
        return true;
    }
}
