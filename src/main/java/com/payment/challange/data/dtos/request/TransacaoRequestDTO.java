package com.payment.challange.data.dtos.request;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.exceptions.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequestDTO {
    @NotBlank(message = "Campo `codigoMerchant` não pode ser nulo ou em branco")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Campo `codigoMerchant` deve ser um UUID válido")
    @Schema(name = "codigoMerchant", description = "ID da transação", example = "3e5fb0a9-bfd6-47e9-a76d-32249d2165ab")
    private String codigoMerchant;

    @DecimalMin(value = "0.0", inclusive = false, message = "O campo `valor` deve ser maior que 0")
    @Schema(name = "valor", description = "Valor da transação", example = "100.00")
    private BigDecimal valor;

    @NotBlank(message = "Campo `descricao` não pode ser nulo ou em branco")
    @Schema(name = "descricao", description = "Descrição da transação", example = "Mercearia do Johnson")
    private String descricao;

    @ValidEnum(enumClass = MetodoPagamento.class, message = "Campo `metodoPagamento` deve seguir o padrão do contrato da API")
    @Schema(name = "metodoPagamento", description = "Informa se o pagamento é em crédito ou débito", example = "debit/credit")
    private String metodoPagamento;

    @Schema(name = "numeroCartao", description = "Número do cartão que está realizando a transação", example = "1234 5678 1234 5678")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter exatamente 16 caracteres")
    @Pattern(regexp = "\\d{16}", message = "O número do cartão deve conter apenas dígitos")
    private String numeroCartao;

    @NotBlank(message = "Campo `nomePortadorCartao` não pode ser nulo ou em branco")
    @Schema(name = "nomePortadorCartao", description = "Nome do portador do Cartão", example = "JOSUE MIRANDA")
    private String nomePortadorCartao;

    @NotBlank(message = "Campo `dataValidadeCartao` não pode ser nulo ou em branco")
    @Schema(name = "dataValidadeCartao", description = "Data de validade do cartão", example = "mm/yy")
    private String dataValidadeCartao;

    @NotBlank(message = "Campo `cvvCartao` não pode ser nulo ou em branco")
    @Size(min = 3, max = 3, message = "O CVV do cartão deve ter exatamente 3 caracteres")
    @Pattern(regexp = "\\d{3}", message = "O número do cartão deve conter apenas dígitos")
    @Schema(name = "cvvCartao", description = "Código de verificação do cartão", example = "111")
    private String cvvCartao;
}
