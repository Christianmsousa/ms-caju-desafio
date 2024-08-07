package com.caju_desafio.ms_caju_desafio.persistence.entity.merchant;


import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;
import com.caju_desafio.ms_caju_desafio.core.helper.JsonLocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchant")
public class MerchantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Long mcc;

    @JsonLocalDateTime
    private LocalDateTime createdAt;

    @JsonLocalDateTime
    private LocalDateTime updatedAt;

    public MerchantEntity(Merchant merchant) {
        this.id = merchant.id();
        this.name = merchant.name();
        this.mcc = merchant.mcc();
        this. createdAt = merchant.createdAt();
        this.updatedAt = merchant.updatedAt();
    }
    public Merchant toMerchant() {
        return new Merchant(id, name, mcc, createdAt, updatedAt);
    }
}
