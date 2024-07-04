package com.payment.challange.data.database.repository;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;

import java.util.List;

public interface TransacaoRepository {
    TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO);
    List<TransacaoResponseDTO> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao);
    TransacaoResponseDTO obterTransacaoPorId(String id);
}
