package com.payment.challange.domain.usecase.impl;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.domain.boundary.TransacaoBoundary;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import com.payment.challange.domain.usecase.TransacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoUseCaseImpl implements TransacaoUseCase {
    private final TransacaoBoundary transacaoBoundary;

    private final ExtratoUseCase extratoUseCase;

    @Override
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        extratoUseCase.processarExtrato(transacaoRequestDTO);
        return transacaoBoundary.criarTransacao(transacaoRequestDTO);
    }

    @Override
    public List<TransacaoResponseDTO> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao) {
        return transacaoBoundary.obterTransacoes(descricao, metodoPagamento, nomePortadorCartao);
    }

    @Override
    public TransacaoResponseDTO obterTransacaoPorId(String id) {
        return transacaoBoundary.obterTransacaoPorId(id);
    }
}
