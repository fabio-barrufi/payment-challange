package com.payment.challange.entrypoint.controller.impl;

import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import com.payment.challange.entrypoint.controller.ExtratoController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ExtratoControllerImpl implements ExtratoController {
    private final ExtratoUseCase extratoUseCase;

    @Override
    public ResponseEntity<BigDecimal> obterSaldoDisponivel(String codigoMerchant) {
        SaldoResponseDTO saldoResponseDTO = extratoUseCase.obterSaldoDisponivel(codigoMerchant);
        return new ResponseEntity<>(saldoResponseDTO.getSaldoDisponivel(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoAguardandoFundos(String codigoMerchant) {
        SaldoResponseDTO saldoResponseDTO = extratoUseCase.obterSaldoAguardandoFundos(codigoMerchant);
        return new ResponseEntity<>(saldoResponseDTO.getSaldoAguardandoFundos(), HttpStatus.OK);
    }
}
