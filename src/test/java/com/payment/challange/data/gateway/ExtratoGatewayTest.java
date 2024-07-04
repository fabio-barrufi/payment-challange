package com.payment.challange.data.gateway;

import com.payment.challange.data.database.repository.ExtratoRepository;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.models.ExtratoModel;
import com.payment.challange.helper.ExtratoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExtratoGatewayTest {

    @Mock
    private ExtratoRepository extratoRepository;

    @InjectMocks
    private ExtratoGateway extratoGateway;

    private final String codigoMerchant = "codigoMerchant";
    private final SaldoResponseDTO saldoResponseDTO = new SaldoResponseDTO();

    private final ExtratoModel extratoModel = ExtratoMock.extratoModelMock();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processarExtrato() {
        givenExtratoRepositoryProcessarExtrato();
        whenProcessarExtratoIsCalled();
        thenVerifyExtratoRepositoryProcessarExtratoCalledOnce();
    }

    @Test
    void obterSaldoDisponivel() {
        givenExtratoRepositoryRetornaSaldoDisponivel();
        whenObterSaldoDisponivelIsCalled();
        thenVerifyExtratoRepositoryObterSaldoDisponivelCalledOnce();
    }

    @Test
    void obterSaldoAguardandoFundos() {
        givenExtratoRepositoryRetornaSaldoAguardandoFundos();
        whenObterSaldoAguardandoFundosIsCalled();
        thenVerifyExtratoRepositoryObterSaldoAguardandoFundosCalledOnce();
    }

    // given

    void givenExtratoRepositoryProcessarExtrato() {
        doNothing().when(extratoRepository).processarExtrato(any());
    }

    void givenExtratoRepositoryRetornaSaldoDisponivel() {
        when(extratoRepository.obterSaldoDisponivel(anyString())).thenReturn(saldoResponseDTO);
    }

    void givenExtratoRepositoryRetornaSaldoAguardandoFundos() {
        when(extratoRepository.obterSaldoAguardandoFundos(anyString())).thenReturn(saldoResponseDTO);
    }

    // when

    void whenProcessarExtratoIsCalled() {
        extratoGateway.processarExtrato(extratoModel);
    }

    void whenObterSaldoDisponivelIsCalled() {
        extratoGateway.obterSaldoDisponivel(codigoMerchant);
    }

    void whenObterSaldoAguardandoFundosIsCalled() {
        extratoGateway.obterSaldoAguardandoFundos(codigoMerchant);
    }

    // then

    void thenVerifyExtratoRepositoryProcessarExtratoCalledOnce() {
        verify(extratoRepository, times(1)).processarExtrato(any());
    }

    void thenVerifyExtratoRepositoryObterSaldoDisponivelCalledOnce() {
        verify(extratoRepository, times(1)).obterSaldoDisponivel(anyString());
    }

    void thenVerifyExtratoRepositoryObterSaldoAguardandoFundosCalledOnce() {
        verify(extratoRepository, times(1)).obterSaldoAguardandoFundos(anyString());
    }
}
