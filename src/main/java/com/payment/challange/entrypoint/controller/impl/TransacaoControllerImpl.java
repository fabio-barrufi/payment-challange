package com.payment.challange.entrypoint.controller.impl;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.domain.usecase.TransacaoUseCase;
import com.payment.challange.entrypoint.controller.TransacaoController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransacaoControllerImpl implements TransacaoController {
    private final TransacaoUseCase transacaoUseCase;

    @Override
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        TransacaoResponseDTO transacaoResponseDTO = transacaoUseCase.criarTransacao(transacaoRequestDTO);

        return new ResponseEntity<>(transacaoResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TransacaoResponseDTO>> obterTransacoes(String descricao, String metodoPagamento, String nomePortadorCartao) {
        List<TransacaoResponseDTO> transacaoResponseDTO = transacaoUseCase.obterTransacoes(descricao, metodoPagamento, nomePortadorCartao);

        return new ResponseEntity<>(transacaoResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransacaoResponseDTO> obterTransacaoPorId(String id) {
        TransacaoResponseDTO transacaoResponseDTO = transacaoUseCase.obterTransacaoPorId(id);

        return new ResponseEntity<>(transacaoResponseDTO, HttpStatus.OK);
    }
}
