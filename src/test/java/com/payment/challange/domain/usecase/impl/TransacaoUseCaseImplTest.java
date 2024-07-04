package com.payment.challange.domain.usecase.impl;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.domain.boundary.TransacaoBoundary;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransacaoUseCaseImplTest {

    @Mock
    private TransacaoBoundary transacaoBoundary;

    @Mock
    private ExtratoUseCase extratoUseCase;

    @InjectMocks
    private TransacaoUseCaseImpl transacaoUseCase;

    private final TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO();
    private final TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTransacao() {
        givenTransacaoBoundaryRetornaTransacaoResponseDTO();
        whenTransacaoUseCaseCriarTransacaoIsCalled();
        thenVerifyExtratoUseCaseProcessarExtratoCalledOnce();
        thenVerifyTransacaoBoundaryCriarTransacaoCalledOnce();
    }

    @Test
    void obterTransacoes() {
        givenTransacaoBoundaryRetornaListaDeTransacaoResponseDTO();
        whenTransacaoUseCaseObterTransacoesIsCalled();
        thenVerifyTransacaoBoundaryObterTransacoesCalledOnce();
    }

    @Test
    void obterTransacaoPorId() {
        givenTransacaoBoundaryRetornaTransacaoResponseDTO();
        whenTransacaoUseCaseObterTransacaoPorIdIsCalled();
        thenVerifyTransacaoBoundaryObterTransacaoPorIdCalledOnce();
    }

    //given
    void givenTransacaoBoundaryRetornaTransacaoResponseDTO() {
        when(transacaoBoundary.criarTransacao(any(TransacaoRequestDTO.class))).thenReturn(transacaoResponseDTO);
        when(transacaoBoundary.obterTransacaoPorId(anyString())).thenReturn(transacaoResponseDTO);
    }

    void givenTransacaoBoundaryRetornaListaDeTransacaoResponseDTO() {
        when(transacaoBoundary.obterTransacoes(anyString(), anyString(), anyString())).thenReturn(List.of(transacaoResponseDTO));
    }

    //when
    void whenTransacaoUseCaseCriarTransacaoIsCalled() {
        transacaoUseCase.criarTransacao(transacaoRequestDTO);
    }

    void whenTransacaoUseCaseObterTransacoesIsCalled() {
        transacaoUseCase.obterTransacoes("descricao", "metodoPagamento", "nomePortadorCartao");
    }

    void whenTransacaoUseCaseObterTransacaoPorIdIsCalled() {
        transacaoUseCase.obterTransacaoPorId("id");
    }

    //then
    void thenVerifyExtratoUseCaseProcessarExtratoCalledOnce() {
        verify(extratoUseCase, times(1)).processarExtrato(any(TransacaoRequestDTO.class));
    }

    void thenVerifyTransacaoBoundaryCriarTransacaoCalledOnce() {
        verify(transacaoBoundary, times(1)).criarTransacao(any(TransacaoRequestDTO.class));
    }

    void thenVerifyTransacaoBoundaryObterTransacoesCalledOnce() {
        verify(transacaoBoundary, times(1)).obterTransacoes(anyString(), anyString(), anyString());
    }

    void thenVerifyTransacaoBoundaryObterTransacaoPorIdCalledOnce() {
        verify(transacaoBoundary, times(1)).obterTransacaoPorId(anyString());
    }
}
