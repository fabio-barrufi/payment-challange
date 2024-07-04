package com.payment.challange.data.mapper;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.data.models.TransacaoModel;
import com.payment.challange.helper.TransacaoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransacaoMapperTest {

    private TransacaoMapper transacaoMapper;
    private TransacaoRequestDTO transacaoRequestDTO;
    private TransacaoModel transacaoModel;
    private TransacaoResponseDTO transacaoResponseDTO;

    @BeforeEach
    void setUp() {
        transacaoMapper = new TransacaoMapperImpl();
    }

    @Test
    void dtoToModelComCamposCorretos() {
        givenValidTransacaoRequestDTO();
        whenDtoToModelCalled();
        thenFieldsShouldBeMappedCorrectlyInModel();
    }

    @Test
    void dtoToModelCamposNulos() {
        givenNullTransacaoRequestDTO();
        whenDtoToModelCalled();
        thenModelShouldReturnNull();
    }

    @Test
    void modelToDtoResponseComCamposCorretos() {
        givenValidTransacaoModel();
        whenModelToDtoResponseCalled();
        thenFieldsShouldBeMappedCorrectlyInResponseDTO();
    }

    @Test
    void modelToDtoResponseComCamposNulos() {
        givenNullTransacaoModel();
        whenModelToDtoResponseCalled();
        thenResponseDTOShouldReturnNull();
    }

    // given

    private void givenValidTransacaoRequestDTO() {
        transacaoRequestDTO = TransacaoMock.transacaoRequestDTOMock();
    }

    private void givenNullTransacaoRequestDTO() {
        transacaoRequestDTO = null;
    }

    private void givenValidTransacaoModel() {
        transacaoModel = TransacaoMock.transacaoModelMock();
    }

    private void givenNullTransacaoModel() {
        transacaoModel = null;
    }

    // when

    private void whenDtoToModelCalled() {
        transacaoModel = transacaoMapper.dtoToModel(transacaoRequestDTO);
    }

    private void whenModelToDtoResponseCalled() {
        transacaoResponseDTO = transacaoMapper.modelToDtoResponse(transacaoModel);
    }

    // then

    private void thenFieldsShouldBeMappedCorrectlyInModel() {
        assertThat(transacaoModel).isNotNull();
        assertThat(transacaoModel.getCodigoMerchant()).isEqualTo(transacaoRequestDTO.getCodigoMerchant());
        assertThat(transacaoModel.getValor()).isEqualByComparingTo(transacaoRequestDTO.getValor());
        assertThat(transacaoModel.getDescricao()).isEqualTo(transacaoRequestDTO.getDescricao());
        assertThat(transacaoModel.getMetodoPagamento()).isEqualTo(transacaoRequestDTO.getMetodoPagamento());
        assertThat(transacaoModel.getNumeroCartao()).isEqualTo(transacaoRequestDTO.getNumeroCartao());
        assertThat(transacaoModel.getNomePortadorCartao()).isEqualTo(transacaoRequestDTO.getNomePortadorCartao());
        assertThat(transacaoModel.getDataValidadeCartao()).isEqualTo(transacaoRequestDTO.getDataValidadeCartao());
        assertThat(transacaoModel.getCvvCartao()).isEqualTo(transacaoRequestDTO.getCvvCartao());
    }

    private void thenModelShouldReturnNull() {
        assertThat(transacaoModel).isNull();
    }

    private void thenFieldsShouldBeMappedCorrectlyInResponseDTO() {
        assertThat(transacaoResponseDTO).isNotNull();
        assertThat(transacaoResponseDTO.getCodigoMerchant()).isEqualTo(transacaoModel.getCodigoMerchant());
        assertThat(transacaoResponseDTO.getValor()).isEqualByComparingTo(transacaoModel.getValor());
        assertThat(transacaoResponseDTO.getDescricao()).isEqualTo(transacaoModel.getDescricao());
        assertThat(transacaoResponseDTO.getMetodoPagamento()).isEqualTo(MetodoPagamento.valueOf(transacaoModel.getMetodoPagamento()));
        assertThat(transacaoResponseDTO.getNumeroCartao()).isEqualTo(transacaoModel.getNumeroCartao());
        assertThat(transacaoResponseDTO.getNomePortadorCartao()).isEqualTo(transacaoModel.getNomePortadorCartao());
        assertThat(transacaoResponseDTO.getDataValidadeCartao()).isEqualTo(transacaoModel.getDataValidadeCartao());
        assertThat(transacaoResponseDTO.getCvvCartao()).isEqualTo(transacaoModel.getCvvCartao());
    }

    private void thenResponseDTOShouldReturnNull() {
        assertThat(transacaoResponseDTO).isNull();
    }
}