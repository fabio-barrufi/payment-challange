package com.payment.challange.data.database.repository.impl;

import com.payment.challange.data.database.repository.ExtratoRepository;
import com.payment.challange.data.dtos.StatusTransacao;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.exceptions.CustomException;
import com.payment.challange.data.models.ExtratoModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExtratoRepositoryImpl implements ExtratoRepository {
    private static final Logger logger = LoggerFactory.getLogger(ExtratoRepositoryImpl.class);
    private final MongoTemplate mongoTemplate;

    @Override
    public void processarExtrato(ExtratoModel extratoModel) {
        try {
            mongoTemplate.save(extratoModel);
        }
        catch (Exception e) {
            logger.error("Erro ao processar extrato. Erro: {}", e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar extrato");
        }
    }

    @Override
    public SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant) {
        Query query = new Query(Criteria.where("codigoMerchant").is(codigoMerchant)
                .and("status").is(StatusTransacao.paid));
        try {
            List<ExtratoModel> extratos = mongoTemplate.find(query, ExtratoModel.class);

            BigDecimal saldoDisponivel = extratos.stream()
                    .map(ExtratoModel::getValorFinal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return SaldoResponseDTO.builder()
                    .saldoDisponivel(saldoDisponivel)
                    .build();
        } catch (Exception e) {
            logger.error("Erro ao obter saldo disponível. Erro: {}", e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter saldo disponível");
        }
    }

    @Override
    public SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant) {
        Query query = new Query(Criteria.where("codigoMerchant").is(codigoMerchant)
                .and("status").is(StatusTransacao.waiting_funds));

        try {
            List<ExtratoModel> extratos = mongoTemplate.find(query, ExtratoModel.class);

            BigDecimal saldoAguardandoFundos = extratos.stream()
                    .map(ExtratoModel::getValorFinal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return SaldoResponseDTO.builder()
                    .saldoAguardandoFundos(saldoAguardandoFundos)
                    .build();
        } catch (Exception e) {
            logger.error("Erro ao obter saldo aguardando fundos. Erro: {}", e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter saldo aguardando fundos");
        }
    }

    @Override
    public List<ExtratoModel> findByDataAtualAndStatus(LocalDateTime dataAtual) {
        try {
            Query query = new Query(Criteria.where("dataPagamento").lt(dataAtual)
                    .and("status").is(StatusTransacao.waiting_funds));

            return mongoTemplate.find(query, ExtratoModel.class);
        } catch (Exception e) {
            logger.error("Erro ao obter extrato com data atual e status. Erro: {}", e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter extrato com data atual e status");
        }
    }
}
