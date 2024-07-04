package com.payment.challange.domain.boundary;

import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.models.ExtratoModel;

public interface ExtratoBoundary {
    void processarExtrato(ExtratoModel extratoModel);
    SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant);
    SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant);
}
