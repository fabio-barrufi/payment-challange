package com.payment.challange.entrypoint.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.domain.usecase.TransacaoUseCase;
import com.payment.challange.entrypoint.controller.impl.TransacaoControllerImpl;
import com.payment.challange.helper.TransacaoMock;
import com.payment.challange.infrastructure.ApiConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class TransacaoControllerTest {

    @InjectMocks
    TransacaoControllerImpl controller;

    @Mock
    TransacaoUseCase useCase;

    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private Gson gson;
    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.gson = new GsonBuilder().create();
    }

    @Test
    void criarTransacao() throws Exception {
        whenPostEndpointIsCalled(ApiConstants.TRANSACAO_BASE_URL);
        thenAssertResponseAndStatus(HttpStatus.CREATED);
        thenAssertUseCaseCriarTransacaoCalledOnce();
    }

    @Test
    void criarTransacaoThrowsCampoInvalidoException() throws Exception {
        givenAnInvalidPostRequest();
        whenPostEndpointIsCalled(ApiConstants.TRANSACAO_BASE_URL);
        thenAssertResponseAndStatus(HttpStatus.BAD_REQUEST);
        thenAssertUseCaseCriarTransacaoNotCalled();
    }

    @Test
    void obterTransacoes() throws Exception {
        whenGetEndpointIsCalled(ApiConstants.TRANSACAO_BASE_URL);
        thenAssertResponseAndStatus(HttpStatus.OK);
        thenAssertUseCaseObterTransacaoCalledOnce();
    }

    @Test
    void obterTransacaoPorId() throws Exception {
        whenGetEndpointIsCalled(ApiConstants.TRANSACAO_BASE_URL + "/123");
        thenAssertResponseAndStatus(HttpStatus.OK);
        thenAssertUseCaseObterTransacaoPorIdCalledOnce();
    }

    //given

    private void givenAnInvalidPostRequest() {
        transacaoRequestDTO.setCodigoMerchant("123");
    }

    //when
    private void whenPostEndpointIsCalled(String url) throws Exception {
        mvcResult =
                mockMvc.perform(
                        post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(transacaoRequestDTO)))
                .andReturn();
    }

    private void whenGetEndpointIsCalled(String url) throws Exception {
        mvcResult =
                mockMvc.perform(
                        get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    //then
    private void thenAssertResponseAndStatus(HttpStatus expectedStatus) {
        Assertions.assertThat(mvcResult.getResponse()).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus.value());
    }

    void thenAssertUseCaseCriarTransacaoCalledOnce(){
        verify(useCase, times(1)).criarTransacao(transacaoRequestDTO);
    }

    void thenAssertUseCaseCriarTransacaoNotCalled(){
        verify(useCase, times(0)).criarTransacao(transacaoRequestDTO);
    }

    void thenAssertUseCaseObterTransacaoCalledOnce(){
        controller.obterTransacoes(
                "descricao",
                "metodoPagamento",
                "nomePortadorCartao"
        );

        verify(useCase).obterTransacoes(
                "descricao",
                "metodoPagamento",
                "nomePortadorCartao"
        );
    }

    void thenAssertUseCaseObterTransacaoPorIdCalledOnce(){
        controller.obterTransacaoPorId("id");

        verify(useCase).obterTransacaoPorId("id");
    }
}