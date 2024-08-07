package com.caju_desafio.ms_caju_desafio.persistence.repository;

import com.caju_desafio.ms_caju_desafio.persistence.entity.wallet.WalletBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalanceEntity, Long> {
    @Modifying
    @Query("UPDATE WalletBalanceEntity wbe SET wbe.balance = :balance, wbe.version = wbe.version + 1" +
            "WHERE wbe.id = :id AND wbe.version = :version")
    int updateBalanceByIdAndVersion(@Param("id") Long id,
                                    @Param("balance") BigDecimal balance,
                                    @Param("version") Long version);
}
