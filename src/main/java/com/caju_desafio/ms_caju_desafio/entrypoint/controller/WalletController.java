package com.caju_desafio.ms_caju_desafio.entrypoint.controller;

import com.caju_desafio.ms_caju_desafio.core.domain.wallet.Wallet;
import com.caju_desafio.ms_caju_desafio.core.exception.WalletException;
import com.caju_desafio.ms_caju_desafio.core.service.wallet.WalletService;
import com.caju_desafio.ms_caju_desafio.entrypoint.payload.request.TransactionRequest;
import com.caju_desafio.ms_caju_desafio.entrypoint.payload.request.WalletCreateRequest;
import com.caju_desafio.ms_caju_desafio.entrypoint.payload.response.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.caju_desafio.ms_caju_desafio.core.exception.ApiError.createApiError;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Wallet createWallet(@Valid @RequestBody WalletCreateRequest walletCreateRequest) {
       return walletService.createWallet(walletCreateRequest.toWallet());
    }

    @GetMapping("{account_id}")
    public Wallet getWalletByAccountId(@PathVariable("account_id") String account_id) {
        return walletService.getWalletByAccountId(account_id)
                             .orElseThrow(() -> new WalletException(createApiError("Wallet not found", NOT_FOUND.value())));
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> transaction(@RequestBody TransactionRequest transactionRequest) {
        var code = walletService.transaction(transactionRequest.toTransaction());
        return ResponseEntity.ok(new TransactionResponse(code));
    }
}
