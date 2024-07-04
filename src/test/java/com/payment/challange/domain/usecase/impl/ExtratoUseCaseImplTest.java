package com.payment.challange.domain.usecase.impl;

import com.payment.challange.data.dtos.StatusTransacao;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.mapper.ExtratoMapper;
import com.payment.challange.data.models.ExtratoModel;
import com.payment.challange.domain.boundary.ExtratoBoundary;
import com.payment.challange.helper.ExtratoMock;
import com.payment.challange.helper.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExtratoUseCaseImplTest {

    @Mock
    private ExtratoBoundary extratoBoundary;

    @Mock
    private ExtratoMapper extratoMapper;

    @InjectMocks
    private ExtratoUseCaseImpl extratoUseCase;

    private final TransacaoRequestDTO transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
    private final ExtratoModel extratoModel = ExtratoMock.extratoModelMock();
    private final SaldoResponseDTO saldoResponseDTO = ExtratoMock.saldoResponseDTOMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processarExtratoCredito() {
        givenExtratoMapperTransacaoDtoToExtratoModel("credit");
        givenExtratoBoundaryProcessarExtrato();
        whenProcessarExtratoIsCalled();
        thenVerifyExtratoMapperTransacaoDtoToExtratoModelCalledOnce();
        thenVerifyExtratoBoundaryProcessarExtratoCalledOnce();
        thenExtratoModelValuesForCredit();
    }

    @Test
    void processarExtratoDebito() {
        givenExtratoMapperTransacaoDtoToExtratoModel("debit");
        givenExtratoBoundaryProcessarExtrato();
        whenProcessarExtratoIsCalled();
        thenVerifyExtratoMapperTransacaoDtoToExtratoModelCalledOnce();
        thenVerifyExtratoBoundaryProcessarExtratoCalledOnce();
        thenExtratoModelValuesForDebit();
    }

    @Test
    void obterSaldoDisponivel() {
        givenExtratoBoundaryRetornaSaldoDisponivel();
        whenObterSaldoDisponivelIsCalled();
        thenVerifyExtratoBoundaryObterSaldoDisponivelCalledOnce();
    }

    @Test
    void obterSaldoAguardandoFundos() {
        givenExtratoBoundaryRetornaSaldoAguardandoFundos();
        whenObterSaldoAguardandoFundosIsCalled();
        thenVerifyExtratoBoundaryObterSaldoAguardandoFundosCalledOnce();
    }

    // given

    void givenExtratoMapperTransacaoDtoToExtratoModel(String metodoPagamento) {
        transacaoRequestDTO.setMetodoPagamento(metodoPagamento);
        when(extratoMapper.transacaoDtoToExtratoModel(any())).thenReturn(extratoModel);
    }

    void givenExtratoBoundaryProcessarExtrato() {
        doNothing().when(extratoBoundary).processarExtrato(any(ExtratoModel.class));
    }

    void givenExtratoBoundaryRetornaSaldoDisponivel() {
        when(extratoBoundary.obterSaldoDisponivel(anyString())).thenReturn(saldoResponseDTO);
    }

    void givenExtratoBoundaryRetornaSaldoAguardandoFundos() {
        when(extratoBoundary.obterSaldoAguardandoFundos(anyString())).thenReturn(saldoResponseDTO);
    }

    // when
    void whenProcessarExtratoIsCalled() {
        extratoUseCase.processarExtrato(transacaoRequestDTO);
    }

    void whenObterSaldoDisponivelIsCalled() {
        extratoUseCase.obterSaldoDisponivel("codigoMerchant");
    }

    void whenObterSaldoAguardandoFundosIsCalled() {
        extratoUseCase.obterSaldoAguardandoFundos("codigoMerchant");
    }

    // then
    void thenVerifyExtratoMapperTransacaoDtoToExtratoModelCalledOnce() {
        verify(extratoMapper, times(1)).transacaoDtoToExtratoModel(any());
    }

    void thenVerifyExtratoBoundaryProcessarExtratoCalledOnce() {
        verify(extratoBoundary, times(1)).processarExtrato(any());
    }

    void thenVerifyExtratoBoundaryObterSaldoDisponivelCalledOnce() {
        verify(extratoBoundary, times(1)).obterSaldoDisponivel(anyString());
    }

    void thenVerifyExtratoBoundaryObterSaldoAguardandoFundosCalledOnce() {
        verify(extratoBoundary, times(1)).obterSaldoAguardandoFundos(anyString());
    }

    void thenExtratoModelValuesForCredit() {
        assertEquals(new BigDecimal("95.00").setScale(2, RoundingMode.HALF_UP), extratoModel.getValorFinal());
        assertEquals(LocalDate.now().plusDays(30), extratoModel.getDataPagamento());
        assertEquals(StatusTransacao.waiting_funds, extratoModel.getStatus());
    }

    void thenExtratoModelValuesForDebit() {
        assertEquals(new BigDecimal("97.00").setScale(2, RoundingMode.HALF_UP), extratoModel.getValorFinal());
        assertEquals(LocalDate.now(), extratoModel.getDataPagamento());
        assertEquals(StatusTransacao.paid, extratoModel.getStatus());
    }
}
