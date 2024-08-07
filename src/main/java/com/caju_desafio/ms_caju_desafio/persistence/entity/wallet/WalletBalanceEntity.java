package com.caju_desafio.ms_caju_desafio.persistence.entity.wallet;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.BalanceType;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.WalletBalance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_balance")
public class WalletBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "balance_type")
    private BalanceType balanceType;

    @Column(precision = 18, scale = 4, nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Long version;

    public WalletBalance toWalletBalance() {
        return new WalletBalance(id, balanceType, balance, version);
    }

    @PrePersist
    public void prePersist() {
        if (this.version == null) {
            this.version = 0L;
        }
    }

    public WalletBalanceEntity(WalletBalance walletBalance) {
        this.id = walletBalance.id();
        this.balance = walletBalance.balance();
        this.balanceType = walletBalance.balanceType();
        this.version = walletBalance.version();
    }
}