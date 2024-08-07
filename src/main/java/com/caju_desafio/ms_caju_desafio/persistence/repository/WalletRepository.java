package com.caju_desafio.ms_caju_desafio.persistence.repository;

import com.caju_desafio.ms_caju_desafio.persistence.entity.wallet.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    Optional<WalletEntity> findByAccountId(String account_id);
}
