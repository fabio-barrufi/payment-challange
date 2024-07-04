package com.payment.challange.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoModel {
    @Id
    private String id;
    private String codigoMerchant;
    private BigDecimal valor;
    private String descricao;
    private String metodoPagamento;
    private String numeroCartao;
    private String nomePortadorCartao;
    private String dataValidadeCartao;
    private String cvvCartao;
    private LocalDate dataTransacao;
}
