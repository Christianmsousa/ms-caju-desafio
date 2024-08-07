package com.caju_desafio.ms_caju_desafio.persistence.entity.wallet;


import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.caju_desafio.ms_caju_desafio.core.domain.wallet.WalletBalance;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import static com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet.calculateTotalAmount;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", unique = true, nullable = false)
    private String accountId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "wallet_id")
    private Set<WalletBalanceEntity> balances;

    public WalletEntity(Wallet wallet) {
        this.id = wallet.id();
        this.accountId = wallet.accountId();
        this.balances = wallet.balances().stream().map(WalletBalanceEntity::new).collect(Collectors.toSet());
    }

    public Wallet toWallet() {
        var balancesConverted = getWalletBalances();
        return new Wallet(id, accountId, calculateTotalAmount(balancesConverted), balancesConverted);
    }

    private Set<WalletBalance> getWalletBalances() {
        return balances.stream()
                        .map(WalletBalanceEntity::toWalletBalance)
                        .collect(Collectors.toSet());
    }
}
