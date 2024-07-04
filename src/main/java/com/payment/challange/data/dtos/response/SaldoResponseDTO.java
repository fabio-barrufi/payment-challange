package com.payment.challange.data.dtos.response;

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
public class SaldoResponseDTO {
    @Schema(description = "Saldo disponível (pagamentos feitos no débito)", example = "1000.00")
    private BigDecimal saldoDisponivel;

    @Schema(description = "Saldo aguardando fundos (pagamentos feitos no crédito)", example = "50.00")
    private BigDecimal saldoAguardandoFundos;
}
