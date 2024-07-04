package com.payment.challange.entrypoint.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import com.payment.challange.entrypoint.controller.impl.ExtratoControllerImpl;
import com.payment.challange.helper.ExtratoMock;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ExtratoControllerTest {

    @InjectMocks
    ExtratoControllerImpl controller;

    @Mock
    ExtratoUseCase useCase;

    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private Gson gson;
    private final String codigoMerchant = "merchant123";

    private final SaldoResponseDTO saldoResponseDTO = ExtratoMock.saldoResponseDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.gson = new GsonBuilder().create();
    }

    @Test
    void obterSaldoDisponivel() throws Exception {
        givenUseCaseObterSaldoDisponivelReturns();
        whenGetEndpointIsCalled(ApiConstants.EXTRATO_BASE_URL + "/disponivel", codigoMerchant);
        thenAssertResponseAndStatus(HttpStatus.OK);
        thenAssertUseCaseObterSaldoDisponivelCalledOnce();
    }

    @Test
    void obterSaldoAguardandoFundos() throws Exception {
        givenUseCaseObterSaldoAguardandoFundosReturns();
        whenGetEndpointIsCalled(ApiConstants.EXTRATO_BASE_URL + "/aguardando-fundos", codigoMerchant);
        thenAssertResponseAndStatus(HttpStatus.OK);
        thenAssertUseCaseObterSaldoAguardandoFundosCalledOnce();
    }

    //given
    private void givenUseCaseObterSaldoDisponivelReturns() {
        given(useCase.obterSaldoDisponivel(codigoMerchant)).willReturn(saldoResponseDTO);
    }

    private void givenUseCaseObterSaldoAguardandoFundosReturns() {
        given(useCase.obterSaldoAguardandoFundos(codigoMerchant)).willReturn(saldoResponseDTO);
    }

    //when
    private void whenGetEndpointIsCalled(String url, String codigoMerchant) throws Exception {
        mvcResult =
                mockMvc.perform(
                                get(url)
                                        .param("codigoMerchant", codigoMerchant)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
    }

    //then
    void thenAssertUseCaseObterSaldoDisponivelCalledOnce(){
        verify(useCase, times(1)).obterSaldoDisponivel(codigoMerchant);
    }

    void thenAssertUseCaseObterSaldoAguardandoFundosCalledOnce(){
        verify(useCase, times(1)).obterSaldoAguardandoFundos(codigoMerchant);
    }

    private void thenAssertResponseAndStatus(HttpStatus expectedStatus) {
        Assertions.assertThat(mvcResult.getResponse()).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus.value());
    }
}
