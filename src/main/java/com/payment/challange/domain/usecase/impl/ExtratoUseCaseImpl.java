package com.payment.challange.domain.usecase.impl;

import com.payment.challange.data.dtos.StatusTransacao;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.mapper.ExtratoMapper;
import com.payment.challange.data.models.ExtratoModel;
import com.payment.challange.domain.boundary.ExtratoBoundary;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExtratoUseCaseImpl implements ExtratoUseCase {
    private final ExtratoBoundary extratoBoundary;

    private final ExtratoMapper extratoMapper;

    @Override
    public void processarExtrato(TransacaoRequestDTO transacaoRequestDTO) {
        ExtratoModel extratoModel = extratoMapper.transacaoDtoToExtratoModel(transacaoRequestDTO);
        if (transacaoRequestDTO.getMetodoPagamento().equals("credit")) {
            extratoModel.setValorFinal(transacaoRequestDTO.getValor().multiply(new BigDecimal("0.95"))
                    .setScale(2, RoundingMode.HALF_UP));
            extratoModel.setDataPagamento(LocalDate.now().plusDays(30));
            extratoModel.setStatus(StatusTransacao.waiting_funds);
        } else if (transacaoRequestDTO.getMetodoPagamento().equals("debit")) {
            extratoModel.setValorFinal(transacaoRequestDTO.getValor().multiply(new BigDecimal("0.97"))
                    .setScale(2, RoundingMode.HALF_UP));
            extratoModel.setDataPagamento(LocalDate.now());
            extratoModel.setStatus(StatusTransacao.paid);
        }

        extratoModel.setId(UUID.randomUUID().toString());

        extratoBoundary.processarExtrato(extratoModel);
    }

    @Override
    public SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant) {
        return extratoBoundary.obterSaldoDisponivel(codigoMerchant);
    }

    @Override
    public SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant) {
        return extratoBoundary.obterSaldoAguardandoFundos(codigoMerchant);
    }
}
