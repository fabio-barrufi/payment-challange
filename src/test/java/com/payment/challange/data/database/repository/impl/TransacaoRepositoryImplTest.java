package com.payment.challange.data.database.repository.impl;

import com.payment.challange.data.database.repository.TransacaoRepository;
import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.data.exceptions.CustomException;
import com.payment.challange.data.mapper.TransacaoMapper;
import com.payment.challange.data.mapper.TransacaoMapperImpl;
import com.payment.challange.data.models.TransacaoModel;
import com.payment.challange.helper.TransacaoMock;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TransacaoMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class TransacaoRepositoryImplTest {
    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    TransacaoMapper mapper;

    TransacaoRepository transacaoRepository;

    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
    private final TransacaoResponseDTO transacaoResponseDTO = TransacaoMock.transacaoResponseDTOMock();
    private final TransacaoModel transacaoModel = TransacaoMock.transacaoModelMock();

    @BeforeEach
    void setUp() {
        transacaoRepository = new TransacaoRepositoryImpl(mongoTemplate, mapper);
    }

    @Test
    void criarTransacao() {
        givenAValidRequestDTOToRepositorySaves();
        thenAssertResponse();
        thenVerifyMongoTemplateCalledOnce();
    }

    @Test
    void obterTransacoes() {
        givenSomeTransacoes();
        thenAssertResponseNotEmpty();
        thenVerifyMongoTemplateCalledForFind();
    }

    @Test
    void obterTransacaoPorId() {
        givenATransacaoId();
        thenAssertSingleTransacao();
        thenVerifyMongoTemplateCalledForFindById();
    }

    @Test
    void obterTransacoesComErro() {
        givenSomeErrorWhenMongoFindCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForFind();
    }

    @Test
    void obterTransacaoPorIdComErro() {
        givenSomeErrorWhenMongoFindByIdCalled();
        thenVerifyErrorMessageAndMongoTemplateCalledForFindById();
    }

    // given

    private void givenSomeErrorWhenMongoFindByIdCalled() {
        when(mongoTemplate.findById(anyString(), eq(TransacaoModel.class))).thenThrow
                (new CustomException(HttpStatus.NOT_FOUND, "Transação não encontrada"));
    }

    private void givenSomeErrorWhenMongoFindCalled() {
        when(mongoTemplate.find(any(Query.class), eq(TransacaoModel.class))).thenThrow
                (new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ""));
    }

    private void givenAValidRequestDTOToRepositorySaves() {
        when(mapper.dtoToModel(transacaoRequestDTO)).thenReturn(transacaoModel);
        when(mongoTemplate.save(transacaoModel)).thenReturn(transacaoModel);
        when(mapper.modelToDtoResponse(transacaoModel)).thenReturn(transacaoResponseDTO);
    }

    private void givenSomeTransacoes() {
        List<TransacaoModel> transacoes = List.of(transacaoModel);
        when(mongoTemplate.find(any(Query.class), eq(TransacaoModel.class))).thenReturn(transacoes);
    }

    private void givenATransacaoId() {
        when(mongoTemplate.findById(anyString(), eq(TransacaoModel.class))).thenReturn(transacaoModel);
        when(mapper.modelToDtoResponse(transacaoModel)).thenReturn(transacaoResponseDTO);
    }

    // then

    private void thenAssertResponse() {
        var response = transacaoRepository.criarTransacao(transacaoRequestDTO);

        assertEquals("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab", response.getCodigoMerchant());
        assertEquals(new BigDecimal("100.00"), response.getValor());
        assertEquals("Mercearia do Johnson", response.getDescricao());
        assertEquals(MetodoPagamento.credit, response.getMetodoPagamento());
        assertEquals("**** **** **** 5678", response.getNumeroCartao());
        assertEquals("JOSUE MIRANDA", response.getNomePortadorCartao());
        assertEquals("12/25", response.getDataValidadeCartao());
        assertEquals("111", response.getCvvCartao());
    }

    private void thenVerifyMongoTemplateCalledOnce() {
        verify(mongoTemplate, times(1)).save(any(TransacaoModel.class));
    }

    private void thenVerifyMongoTemplateCalledForFind() {
        verify(mongoTemplate).find(any(Query.class), eq(TransacaoModel.class));
    }

    private void thenVerifyMongoTemplateCalledForFindById() {
        verify(mongoTemplate).findById(anyString(), eq(TransacaoModel.class));
    }

    private void thenAssertSingleTransacao() {
        var response = transacaoRepository.obterTransacaoPorId("123");

        assertEquals("3e5fb0a9-bfd6-47e9-a76d-32249d2165ab", response.getCodigoMerchant());
        assertEquals(new BigDecimal("100.00"), response.getValor());
        assertEquals("Mercearia do Johnson", response.getDescricao());
        assertEquals(MetodoPagamento.credit, response.getMetodoPagamento());
        assertEquals("**** **** **** 5678", response.getNumeroCartao());
        assertEquals("JOSUE MIRANDA", response.getNomePortadorCartao());
        assertEquals("12/25", response.getDataValidadeCartao());
        assertEquals("111", response.getCvvCartao());
    }

    private void thenAssertResponseNotEmpty() {
        var response = transacaoRepository.obterTransacoes(null, null, null);
        assertFalse(response.isEmpty());
    }

    private void thenVerifyErrorMessageAndMongoTemplateCalledForFind() {
        Exception exception = assertThrows(CustomException.class, () -> {
            transacaoRepository.obterTransacoes(null, null, null);
        });
        assertEquals("Erro ao buscar transações: ", exception.getMessage());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(TransacaoModel.class));
    }

    private void thenVerifyErrorMessageAndMongoTemplateCalledForFindById() {
        Exception exception = assertThrows(CustomException.class, () -> {
            transacaoRepository.obterTransacaoPorId("123");
        });
        assertEquals("Transação não encontrada", exception.getMessage());
        verify(mongoTemplate, times(1)).findById(anyString(), eq(TransacaoModel.class));
    }
}
