package com.payment.challange.data.models;

import com.payment.challange.data.dtos.MetodoPagamento;
import com.payment.challange.data.dtos.StatusTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "statements")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoModel {
    @Id
    private String id;
    private String codigoMerchant;
    private BigDecimal valor;
    private BigDecimal valorFinal;
    private MetodoPagamento metodoPagamento;
    private StatusTransacao status;
    private String codigoCartao;
    private String nomePortador;
    private LocalDate dataPagamento;
}
