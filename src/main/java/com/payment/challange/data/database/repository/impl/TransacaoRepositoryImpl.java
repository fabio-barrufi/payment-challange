package com.payment.challange.data.database.repository.impl;

import com.payment.challange.data.database.repository.TransacaoRepository;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.data.exceptions.CustomException;
import com.payment.challange.data.mapper.TransacaoMapper;
import com.payment.challange.data.models.TransacaoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TransacaoRepositoryImpl implements TransacaoRepository {
    private final MongoTemplate mongoTemplate;
    private final TransacaoMapper transacaoMapper;

    @Override
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        TransacaoModel transacaoModel = transacaoMapper.dtoToModel(transacaoRequestDTO);
        transacaoModel.setDataTransacao(LocalDate.now());
        transacaoModel.setId(UUID.randomUUID().toString());

        String numeroCartao = transacaoRequestDTO.getNumeroCartao();
        String numeroCartaoUltimosDigitos = formatarNumeroCartao(numeroCartao.substring(numeroCartao.length() - 4));

        transacaoModel.setNumeroCartao(numeroCartaoUltimosDigitos);
        try {
            mongoTemplate.save(transacaoModel);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar transação: " + e.getMessage());
        }

        return transacaoMapper.modelToDtoResponse(transacaoModel);
    }

    private String formatarNumeroCartao(String ultimosDigitos) {
        StringBuilder sb = new StringBuilder();
        sb.append("**** **** **** ");
        sb.append(ultimosDigitos);
        return sb.toString();
    }

    @Override
    public List<TransacaoResponseDTO> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao) {
        Query query = new Query();
        Optional.ofNullable(descricao)
                .ifPresent(d -> query.addCriteria(Criteria.where("descricao").regex(d, "i")));
        Optional.ofNullable(metodoPagamento)
                .ifPresent(mp -> query.addCriteria(Criteria.where("metodoPagamento").is(mp)));
        Optional.ofNullable(nomePortadorCartao)
                .ifPresent(np -> query.addCriteria(Criteria.where("nomePortadorCartao").regex(np, "i")));

        try {
            List<TransacaoModel> transacoes = mongoTemplate.find(query, TransacaoModel.class);
            return transacoes.stream().map(transacaoMapper::modelToDtoResponse).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar transações: " + e.getMessage());

        }
    }

    @Override
    public TransacaoResponseDTO obterTransacaoPorId(String id) {
        Optional<TransacaoModel> transacaoModel = Optional.ofNullable(mongoTemplate.findById(id, TransacaoModel.class));
        return transacaoModel.map(transacaoMapper::modelToDtoResponse)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Transação não encontrada"));
    }
}
