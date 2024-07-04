package com.payment.challange.entrypoint.controller.impl;

import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.domain.usecase.ExtratoUseCase;
import com.payment.challange.helper.ExtratoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExtratoControllerImplTest {

    @Mock
    private ExtratoUseCase extratoUseCase;

    @InjectMocks
    private ExtratoControllerImpl extratoController;

    private final SaldoResponseDTO saldoResponseDTO = ExtratoMock.saldoResponseDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obterSaldoDisponivel() {
        givenUseCaseReturnsSaldoResponseDTO();
        whenControllerObterSaldoDisponivelIsCalled();
        thenVerifyUseCaseObterSaldoDisponivelCalledOnce();
    }

    @Test
    void obterSaldoAguardandoFundos() {
        givenUseCaseReturnsSaldoResponseDTO();
        whenControllerObterSaldoAguardandoFundosIsCalled();
        thenVerifyUseCaseObterSaldoAguardandoFundosCalledOnce();
    }

    //given
    void givenUseCaseReturnsSaldoResponseDTO() {
        when(extratoUseCase.obterSaldoDisponivel(anyString())).thenReturn(saldoResponseDTO);
        when(extratoUseCase.obterSaldoAguardandoFundos(anyString())).thenReturn(saldoResponseDTO);
    }

    //when
    void whenControllerObterSaldoDisponivelIsCalled() {
        extratoController.obterSaldoDisponivel("codigoMerchant");
    }

    void whenControllerObterSaldoAguardandoFundosIsCalled() {
        extratoController.obterSaldoAguardandoFundos("codigoMerchant");
    }

    //then
    void thenVerifyUseCaseObterSaldoDisponivelCalledOnce() {
        verify(extratoUseCase, times(1)).obterSaldoDisponivel(anyString());
    }

    void thenVerifyUseCaseObterSaldoAguardandoFundosCalledOnce() {
        verify(extratoUseCase, times(1)).obterSaldoAguardandoFundos(anyString());
    }
}
