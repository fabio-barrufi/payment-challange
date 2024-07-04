package com.payment.challange.data.gateway;

import com.payment.challange.data.database.repository.TransacaoRepository;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.helper.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransacaoGatewayTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoGateway transacaoGateway;

    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
    private final TransacaoResponseDTO transacaoResponseDTO = TransacaoMock.transacaoResponseDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTransacao() {
        givenTransacaoRepositoryCriaTransacao();
        whenCriarTransacaoIsCalled();
        thenVerifyTransacaoRepositoryCriaTransacaoCalledOnce();
    }

    @Test
    void obterTransacoes() {
        givenTransacaoRepositoryObterTransacoes();
        whenObterTransacoesIsCalled();
        thenVerifyTransacaoRepositoryObterTransacoesCalledOnce();
    }

    @Test
    void obterTransacaoPorId() {
        givenTransacaoRepositoryObterTransacaoPorId();
        whenObterTransacaoPorIdIsCalled();
        thenVerifyTransacaoRepositoryObterTransacaoPorIdCalledOnce();
    }

    // given
    void givenTransacaoRepositoryCriaTransacao() {
        when(transacaoRepository.criarTransacao(any())).thenReturn(transacaoResponseDTO);
    }

    void givenTransacaoRepositoryObterTransacoes() {
        when(transacaoRepository.obterTransacoes(anyString(), anyString(), anyString())).thenReturn(Collections.singletonList(transacaoResponseDTO));
    }

    void givenTransacaoRepositoryObterTransacaoPorId() {
        when(transacaoRepository.obterTransacaoPorId(anyString())).thenReturn(transacaoResponseDTO);
    }

    // when
    void whenCriarTransacaoIsCalled() {
        transacaoGateway.criarTransacao(transacaoRequestDTO);
    }

    void whenObterTransacoesIsCalled() {
        transacaoGateway.obterTransacoes("descricao", "metodoPagamento", "nomePortadorCartao");
    }

    void whenObterTransacaoPorIdIsCalled() {
        String id = "id";
        transacaoGateway.obterTransacaoPorId(id);
    }

    // then

    void thenVerifyTransacaoRepositoryCriaTransacaoCalledOnce() {
        verify(transacaoRepository, times(1)).criarTransacao(any());
    }

    void thenVerifyTransacaoRepositoryObterTransacoesCalledOnce() {
        verify(transacaoRepository, times(1)).obterTransacoes(anyString(), anyString(), anyString());
    }

    void thenVerifyTransacaoRepositoryObterTransacaoPorIdCalledOnce() {
        verify(transacaoRepository, times(1)).obterTransacaoPorId(anyString());
    }
}
