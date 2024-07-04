package com.payment.challange.entrypoint.controller.impl;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.domain.usecase.TransacaoUseCase;
import com.payment.challange.helper.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransacaoControllerImplTest {

    @Mock
    private TransacaoUseCase useCase;

    @InjectMocks
    private TransacaoControllerImpl controller;

    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
    private final TransacaoResponseDTO transacaoResponseDTO = TransacaoMock.transacaoResponseDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTransacao() {
        givenUseCaseReturnsTransacaoResponseDTO();
        whenControllerCriarTransacaoIsCalled();
        thenVerifyUseCaseCriarTransacaoCalledOnce();
    }

    @Test
    void obterTransacoes() {
        givenUseCaseReturnsTransacaoResponseDTOList();
        whenControllerObterTransacoesIsCalled();
        thenVerifyUseCaseObterTransacoesCalledOnce();
    }

    @Test
    void obterTransacaoPorId() {
        givenUseCaseReturnsTransacaoResponseDTO();
        whenControllerObterTransacaoPorIdIsCalled();
        thenVerifyUseCaseObterTransacaoPorIdCalledOnce();
    }

    //given
    void givenUseCaseReturnsTransacaoResponseDTO() {
        when(useCase.criarTransacao(any(TransacaoRequestDTO.class))).thenReturn(transacaoResponseDTO);
        when(useCase.obterTransacaoPorId(anyString())).thenReturn(transacaoResponseDTO);
    }

    void givenUseCaseReturnsTransacaoResponseDTOList() {
        List<TransacaoResponseDTO> transacaoResponseDTOList = List.of(transacaoResponseDTO);
        when(useCase.obterTransacoes(anyString(), anyString(), anyString())).thenReturn(transacaoResponseDTOList);
    }

    //when
    void whenControllerCriarTransacaoIsCalled() {
        controller.criarTransacao(transacaoRequestDTO);
    }

    void whenControllerObterTransacoesIsCalled() {
        controller.obterTransacoes("descricao", "metodoPagamento", "nomePortadorCartao");
    }

    void whenControllerObterTransacaoPorIdIsCalled() {
        controller.obterTransacaoPorId("id");
    }

    //then
    void thenVerifyUseCaseCriarTransacaoCalledOnce() {
        verify(useCase, times(1)).criarTransacao(any(TransacaoRequestDTO.class));
    }

    void thenVerifyUseCaseObterTransacoesCalledOnce() {
        verify(useCase, times(1)).obterTransacoes(anyString(), anyString(), anyString());
    }

    void thenVerifyUseCaseObterTransacaoPorIdCalledOnce() {
        verify(useCase, times(1)).obterTransacaoPorId(anyString());
    }
}
