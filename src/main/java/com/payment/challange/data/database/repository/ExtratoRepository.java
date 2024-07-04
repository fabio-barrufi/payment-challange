package com.payment.challange.data.database.repository;

import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.models.ExtratoModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ExtratoRepository {
    void processarExtrato(ExtratoModel extratoModel);
    SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant);
    SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant);
    List<ExtratoModel> findByDataAtualAndStatus(LocalDateTime dataAtual);

}
