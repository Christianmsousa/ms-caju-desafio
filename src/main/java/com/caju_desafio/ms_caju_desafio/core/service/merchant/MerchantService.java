package com.caju_desafio.ms_caju_desafio.core.service.merchant;

import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;
import com.caju_desafio.ms_caju_desafio.core.exception.MerchantException;
import com.caju_desafio.ms_caju_desafio.core.gateway.MerchantGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.caju_desafio.ms_caju_desafio.core.exception.ApiError.createApiError;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantGateway merchantGateway;

    public Merchant createMerchant(String name, Long mcc) {
        var nameFormatted = name.toUpperCase();
        merchantGateway.findByName(nameFormatted).ifPresent(e -> {
            throw new MerchantException(createApiError("Merchant already registered", CONFLICT.value()));
        });
        var now = LocalDateTime.now();
        var merchant = new Merchant(null, nameFormatted, mcc, now, now);

        return merchantGateway.persist(merchant);
    }
}
