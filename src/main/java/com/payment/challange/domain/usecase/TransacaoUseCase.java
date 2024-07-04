package com.payment.challange.domain.usecase;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;

import java.util.List;

public interface TransacaoUseCase {
    TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO);
    List<TransacaoResponseDTO> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao);
    TransacaoResponseDTO obterTransacaoPorId(String id);
}
