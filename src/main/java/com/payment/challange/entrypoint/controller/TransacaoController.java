package com.payment.challange.entrypoint.controller;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.infrastructure.ApiConstants;
import com.payment.challange.infrastructure.ApiResponseConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Transacao Service")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface TransacaoController {

    @Operation(summary = "Efetua transações",
            description = "<h4><b>Endpoint para efetuar uma transação.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = ApiResponseConstants.STATUS_201_CREATED),
                    @ApiResponse(responseCode = "400", description = ApiResponseConstants.STATUS_400_BAD_REQUEST),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY),
                    @ApiResponse(responseCode = "500", description = ApiResponseConstants.STATUS_500_INTERNAL_SERVER_ERROR),
            })
    @PostMapping(ApiConstants.TRANSACAO_BASE_URL)
    ResponseEntity<TransacaoResponseDTO> criarTransacao(@Valid @RequestBody TransacaoRequestDTO transacaoRequestDTO);

    @Operation(summary = "Busca transações",
            description = "<h4><b>Endpoint para buscar todas as transações.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK),
                    @ApiResponse(responseCode = "400", description = ApiResponseConstants.STATUS_400_BAD_REQUEST),
                    @ApiResponse(responseCode = "404", description = ApiResponseConstants.STATUS_404_NOT_FOUND),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY),
                    @ApiResponse(responseCode = "500", description = ApiResponseConstants.STATUS_500_INTERNAL_SERVER_ERROR),
            })
    @GetMapping(ApiConstants.TRANSACAO_BASE_URL)
    ResponseEntity<List<TransacaoResponseDTO>> obterTransacoes(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String metodoPagamento,
            @RequestParam(required = false) String nomePortadorCartao
    );

    @Operation(summary = "Busca transações por id",
            description = "<h4><b>Endpoint para buscar transações pelo seu id.</b></h4>")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ApiResponseConstants.STATUS_200_OK),
                    @ApiResponse(responseCode = "404", description = ApiResponseConstants.STATUS_404_NOT_FOUND),
                    @ApiResponse(responseCode = "422", description = ApiResponseConstants.STATUS_422_UNPROCESSABLE_ENTITY),
                    @ApiResponse(responseCode = "500", description = ApiResponseConstants.STATUS_500_INTERNAL_SERVER_ERROR),
            })
    @GetMapping(ApiConstants.TRANSACAO_BASE_URL + "/{id}")
    ResponseEntity<TransacaoResponseDTO> obterTransacaoPorId(@PathVariable String id);
}
