package com.payment.challange.domain.usecase;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;

public interface ExtratoUseCase {
    void processarExtrato(TransacaoRequestDTO transacaoRequestDTO);
    SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant);
    SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant);
}
