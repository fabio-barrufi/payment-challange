package com.payment.challange.helper;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.data.models.TransacaoModel;

import java.math.BigDecimal;

public class TransacaoMock {
    public static TransacaoResponseDTO transacaoResponseDTOMock() {
        return TransacaoResponseDTO.builder()
                .codigoMerchant("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
                .valor(new BigDecimal("100.00"))
                .descricao("Mercearia do Johnson")
                .metodoPagamento(MetodoPagamento.credit)
                .numeroCartao("**** **** **** 5678")
                .nomePortadorCartao("JOSUE MIRANDA")
                .dataValidadeCartao("12/25")
                .cvvCartao("111")
                .build();
    }

    public static TransacaoRequestDTO transacaoRequestDTOMock() {
        return TransacaoRequestDTO.builder()
                .codigoMerchant("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
                .valor(new BigDecimal("100.00"))
                .descricao("Mercearia do Johnson")
                .metodoPagamento("credit")
                .numeroCartao("1234567812345678")
                .nomePortadorCartao("JOSUE MIRANDA")
                .dataValidadeCartao("12/25")
                .cvvCartao("111")
                .build();
    }

    public static TransacaoModel transacaoModelMock() {
        return TransacaoModel.builder()
                .id("123")
                .codigoMerchant("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
                .valor(new BigDecimal("100.00"))
                .descricao("Mercearia do Johnson")
                .metodoPagamento("credit")
                .numeroCartao("**** **** **** 5678")
                .nomePortadorCartao("JOSUE MIRANDA")
                .dataValidadeCartao("12/25")
                .cvvCartao("111")
                .build();
    }
}
