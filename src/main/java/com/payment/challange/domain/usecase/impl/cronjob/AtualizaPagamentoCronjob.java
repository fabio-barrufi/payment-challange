package com.payment.challange.domain.usecase.impl.cronjob;

import com.payment.challange.data.database.repository.ExtratoRepository;
import com.payment.challange.data.dtos.StatusTransacao;
import com.payment.challange.data.models.ExtratoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtualizaPagamentoCronjob {
    @Autowired
    private ExtratoRepository extratoRepository;

    private static final Logger logger = LoggerFactory.getLogger(AtualizaPagamentoCronjob.class);


    @Scheduled(cron = "0 * * * * ?") // Executa todo minuto
    public void atualizarStatusTransacoes() {
        LocalDateTime dataAtual = LocalDateTime.now();
        logger.info("Executando cron job em: " + dataAtual);

        List<ExtratoModel> extratosParaAtualizar = extratoRepository.findByDataAtualAndStatus(
                LocalDateTime.now());

        extratosParaAtualizar.stream()
                .peek(extrato -> extrato.setStatus(StatusTransacao.paid))
                .forEach(extrato -> {
                    try {
                        extratoRepository.processarExtrato(extrato);
                        logger.info("Atualizado status da transação: " + extrato.getId());
                    } catch (Exception e) {
                        logger.error("Erro ao atualizar status da transação: " + e.getMessage());
                    }
                });
    }
}
