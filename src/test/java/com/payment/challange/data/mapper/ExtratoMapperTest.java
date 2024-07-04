package com.payment.challange.data.mapper;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.models.ExtratoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExtratoMapperTest {

    private ExtratoMapper extratoMapper;
    private TransacaoRequestDTO transacaoRequestDTO;
    private ExtratoModel extratoModel;

    @BeforeEach
    void setUp() {
        extratoMapper = new ExtratoMapperImpl();
    }

    @Test
    void transacaoDtoToExtratoModelComCamposCorretos() {
        givenValidTransacaoRequestDTO();
        whenTransacaoDtoToExtratoModelCalled();
        thenFieldsShouldBeMappedCorrectly();
    }

    @Test
    void transacaoDtoToExtratoModelNull() {
        givenNullTransacaoRequestDTO();
        whenTransacaoDtoToExtratoModelCalled();
        thenShouldReturnNull();
    }

    @Test
    void transacaoDtoToExtratoModelMetodoPagamentoNull() {
        givenTransacaoRequestDTONullMetodoPagamento();
        whenTransacaoDtoToExtratoModelCalled();
        thenShouldHandleNullMetodoPagamento();
    }

    // given

    private void givenValidTransacaoRequestDTO() {
        transacaoRequestDTO = new TransacaoRequestDTO();
        transacaoRequestDTO.setCodigoMerchant("merchant123");
        transacaoRequestDTO.setValor(new BigDecimal("100.00"));
        transacaoRequestDTO.setMetodoPagamento("credit");
    }

    private void givenNullTransacaoRequestDTO() {
        transacaoRequestDTO = null;
    }

    private void givenTransacaoRequestDTONullMetodoPagamento() {
        transacaoRequestDTO = new TransacaoRequestDTO();
        transacaoRequestDTO.setCodigoMerchant("merchant123");
        transacaoRequestDTO.setValor(new BigDecimal("100.00"));
        transacaoRequestDTO.setMetodoPagamento(null);
    }

    // when

    private void whenTransacaoDtoToExtratoModelCalled() {
        extratoModel = extratoMapper.transacaoDtoToExtratoModel(transacaoRequestDTO);
    }

    // then

    private void thenFieldsShouldBeMappedCorrectly() {
        assertThat(extratoModel).isNotNull();
        assertThat(extratoModel.getCodigoMerchant()).isEqualTo("merchant123");
        assertThat(extratoModel.getValor()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(extratoModel.getMetodoPagamento()).isEqualTo(MetodoPagamento.credit);
    }

    private void thenShouldReturnNull() {
        assertThat(extratoModel).isNull();
    }

    private void thenShouldHandleNullMetodoPagamento() {
        assertThat(extratoModel).isNotNull();
        assertThat(extratoModel.getCodigoMerchant()).isEqualTo("merchant123");
        assertThat(extratoModel.getValor()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(extratoModel.getMetodoPagamento()).isNull();
    }
}
