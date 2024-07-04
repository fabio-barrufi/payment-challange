package com.payment.challange.entrypoint.controller;

import com.payment.challange.infrastructure.ApiConstants;
import com.payment.challange.infrastructure.ApiResponseConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Tag(name = "Extrato Service")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface ExtratoController {

    @Operation(summary = "Busca saldo disponível",
            description = "<h4><b>Endpoint para buscar saldo disponível.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK),
                    @ApiResponse(responseCode = "400", description = ApiResponseConstants.STATUS_400_BAD_REQUEST),
                    @ApiResponse(responseCode = "404", description = ApiResponseConstants.STATUS_404_NOT_FOUND),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY),
                    @ApiResponse(responseCode = "500", description = ApiResponseConstants.STATUS_500_INTERNAL_SERVER_ERROR),
            })
    @GetMapping(ApiConstants.EXTRATO_BASE_URL + "/disponivel")
    ResponseEntity<BigDecimal> obterSaldoDisponivel(@RequestParam String codigoMerchant);


    @Operation(summary = "Busca saldo que ainda está aguardando fundos",
            description = "<h4><b>Endpoint para buscar saldo que ainda está aguardando fundos.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK),
                    @ApiResponse(responseCode = "400", description = ApiResponseConstants.STATUS_400_BAD_REQUEST),
                    @ApiResponse(responseCode = "404", description = ApiResponseConstants.STATUS_404_NOT_FOUND),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY),
                    @ApiResponse(responseCode = "500", description = ApiResponseConstants.STATUS_500_INTERNAL_SERVER_ERROR),
            })
    @GetMapping(ApiConstants.EXTRATO_BASE_URL + "/aguardando-fundos")
    ResponseEntity<BigDecimal> obterSaldoAguardandoFundos(@RequestParam String codigoMerchant);
}
