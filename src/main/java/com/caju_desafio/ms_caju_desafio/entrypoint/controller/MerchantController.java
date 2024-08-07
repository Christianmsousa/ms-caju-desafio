package com.caju_desafio.ms_caju_desafio.entrypoint.controller;


import com.caju_desafio.ms_caju_desafio.core.domain.merchant.Merchant;
import com.caju_desafio.ms_caju_desafio.core.service.merchant.MerchantService;
import com.caju_desafio.ms_caju_desafio.entrypoint.payload.request.MerchantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public Merchant createMerchant(@RequestBody MerchantCreateRequest merchantCreateRequest) {
        return merchantService.createMerchant(merchantCreateRequest.name(), merchantCreateRequest.mcc());
    }

}
