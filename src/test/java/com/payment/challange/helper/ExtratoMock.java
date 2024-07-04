package com.payment.challange.helper;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.StatusTransacao;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.models.ExtratoModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExtratoMock {
    public static SaldoResponseDTO saldoResponseDTOMock() {
        return SaldoResponseDTO.builder()
                .saldoDisponivel(new BigDecimal("1000.00"))
                .saldoAguardandoFundos(new BigDecimal("50.00"))
                .build();
    }

    public static ExtratoModel extratoModelMock() {
        return ExtratoModel.builder()
                .id("1")
                .codigoMerchant("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
                .valor(new BigDecimal("100.00"))
                .valorFinal(new BigDecimal("95.00"))
                .metodoPagamento(MetodoPagamento.credit)
                .status(StatusTransacao.paid)
                .codigoCartao("6549873025634501")
                .nomePortador("João da Silva")
                .dataPagamento(LocalDate.of(2024, 6, 28))
                .build();
    }
}
