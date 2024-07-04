package com.payment.challange.data.gateway;

import com.payment.challange.data.database.repository.TransacaoRepository;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.domain.boundary.TransacaoBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoGateway implements TransacaoBoundary {
    private final TransacaoRepository transacaoRepository;

    @Override
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        return transacaoRepository.criarTransacao(transacaoRequestDTO);
    }

    @Override
    public List<TransacaoResponseDTO> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao) {
        return transacaoRepository.obterTransacoes(descricao, metodoPagamento, nomePortadorCartao);
    }

    @Override
    public TransacaoResponseDTO obterTransacaoPorId(String id) {
        return transacaoRepository.obterTransacaoPorId(id);
    }
}
