package com.caju_desafio.ms_caju_desafio.core.gateway;

import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;

import java.util.Optional;

public interface MerchantGateway {
    Optional<Merchant> findByName(String name);
    Merchant persist(Merchant merchant);
}
