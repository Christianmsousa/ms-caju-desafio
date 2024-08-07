package com.caju_desafio.ms_caju_desafio.persistence.repository;

import com.caju_desafio.ms_caju_desafio.persistence.entity.merchant.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {

    Optional<MerchantEntity> findOptionalByName(String name);
}
