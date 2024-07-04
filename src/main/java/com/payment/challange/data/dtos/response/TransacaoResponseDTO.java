package com.payment.challange.data.dtos.response;

import com.payment.challange.data.dtos.MetodoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseDTO {
    @Schema(description = "Código do comerciante", example = "3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
    private String codigoMerchant;
    @Schema(description = "Valor da transação", example = "100.00")
    private BigDecimal valor;
    @Schema(description = "Descrição da transação", example = "Mercearia do Johnson")
    private String descricao;
    @Schema(description = "Método de pagamento utilizado na transação", example = "credit")
    private MetodoPagamento metodoPagamento;
    @Schema(description = "Número do cartão", example = "1234 5678 1234 5678")
    private String numeroCartao;
    @Schema(description = "Nome do portador do cartão", example = "João da Silva")
    private String nomePortadorCartao;
    @Schema(description = "Data de validade do cartão", example = "12/25")
    private String dataValidadeCartao;
    @Schema(description = "CVV do cartão", example = "123")
    private String cvvCartao;
}
