package com.payment.challange.data.database.repository.impl;

import com.payment.challange.data.database.repository.ExtratoRepository;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.exceptions.CustomException;
import com.payment.challange.data.mapper.ExtratoMapperImpl;
import com.payment.challange.data.models.ExtratoModel;
import com.payment.challange.helper.ExtratoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ExtratoMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class ExtratoRepositoryImplTest {
    @Mock
    MongoTemplate mongoTemplate;

    ExtratoRepository extratoRepository;

    private final ExtratoModel extratoModel = ExtratoMock.extratoModelMock();

    @BeforeEach
    void setUp() {
        extratoRepository = new ExtratoRepositoryImpl(mongoTemplate);
    }

    @Test
    void processarExtrato() {
        givenAValidExtratoModelToRepositorySaves();
        thenVerifyMongoTemplateCalledOnceForSave();
    }

    @Test
    void processarExtratoComErro() {
        givenSomeErrorWhenMongoSaveCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForSave();
    }

    @Test
    void obterSaldoDisponivel() {
        givenSomeExtratos();
        thenAssertSaldoDisponivel();
        thenVerifyMongoTemplateCalledForFind();
    }

    @Test
    void obterSaldoDisponivelComErro() {
        givenSomeErrorWhenMongoFindCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForFind();
    }

    @Test
    void obterSaldoAguardandoFundos() {
        givenSomeExtratos();
        thenAssertSaldoAguardandoFundos();
        thenVerifyMongoTemplateCalledForFind();
    }

    @Test
    void obterSaldoAguardandoFundosComErro() {
        givenSomeErrorWhenMongoFindCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForFind();
    }

    @Test
    void findByDataAtualAndStatus() {
        givenSomeExtratos();
        thenAssertExtratosByDataAtualAndStatus();
        thenVerifyMongoTemplateCalledForFind();
    }

    @Test
    void findByDataAtualAndStatusComErro() {
        givenSomeErrorWhenMongoFindCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForFind();
    }

    // given

    private void givenAValidExtratoModelToRepositorySaves() {
        when(mongoTemplate.save(extratoModel)).thenReturn(extratoModel);
    }

    private void givenSomeErrorWhenMongoSaveCalled() {
        when(mongoTemplate.save(any(ExtratoModel.class))).thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar extrato"));
    }

    private void givenSomeExtratos() {
        List<ExtratoModel> extratos = List.of(extratoModel);
        when(mongoTemplate.find(any(Query.class), eq(ExtratoModel.class))).thenReturn(extratos);
    }

    private void givenSomeErrorWhenMongoFindCalled() {
        when(mongoTemplate.find(any(Query.class), eq(ExtratoModel.class))).thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar transações"));
    }

    // then

    private void thenVerifyMongoTemplateCalledOnceForSave() {
        extratoRepository.processarExtrato(extratoModel);
        verify(mongoTemplate, times(1)).save(extratoModel);
    }

    private void thenVerifyErrorMessageAndMongoTemplateCalledForSave() {
        Exception exception = assertThrows(CustomException.class, () -> {
            extratoRepository.processarExtrato(extratoModel);
        });
        assertEquals("Erro ao processar extrato", exception.getMessage());
        verify(mongoTemplate, times(1)).save(any(ExtratoModel.class));
    }

    private void thenAssertSaldoDisponivel() {
        SaldoResponseDTO response = extratoRepository.obterSaldoDisponivel("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab");
        assertEquals(new BigDecimal("95.00"), response.getSaldoDisponivel());
    }

    private void thenAssertSaldoAguardandoFundos() {
        SaldoResponseDTO response = extratoRepository.obterSaldoAguardandoFundos("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab");
        assertEquals(new BigDecimal("95.00"), response.getSaldoAguardandoFundos());
    }

    private void thenAssertExtratosByDataAtualAndStatus() {
        LocalDateTime dataAtual = LocalDateTime.now();
        List<ExtratoModel> extratos = extratoRepository.findByDataAtualAndStatus(dataAtual);
        assertFalse(extratos.isEmpty());
    }

    private void thenVerifyMongoTemplateCalledForFind() {
        verify(mongoTemplate, atLeastOnce()).find(any(Query.class), eq(ExtratoModel.class));
    }

    private void thenVerifyErrorMessageAndMongoTemplateCalledForFind() {
        Exception exception = assertThrows(CustomException.class, () -> {
            extratoRepository.obterSaldoDisponivel("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab");
        });
        assertEquals("Erro ao obter saldo disponível", exception.getMessage());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(ExtratoModel.class));
    }
}
