package com.caju_desafio.ms_caju_desafio.persistence.impl;

import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;
import com.caju_desafio.ms_caju_desafio.core.gateway.MerchantGateway;
import com.caju_desafio.ms_caju_desafio.persistence.entity.merchant.MerchantEntity;
import com.caju_desafio.ms_caju_desafio.persistence.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MerchantGatewayImpl implements MerchantGateway {

    private final MerchantRepository merchantRepository;

    @Override
    public Optional<Merchant> findByName(String name) {
        return merchantRepository.findOptionalByName(name).map(MerchantEntity::toMerchant);
    }

    @Override
    public Merchant persist(Merchant merchant) {
        return merchantRepository.save(new MerchantEntity(merchant)).toMerchant();
    }
}
