package com.payment.challange.data.gateway;

import com.payment.challange.data.database.repository.ExtratoRepository;
import com.payment.challange.data.dtos.response.SaldoResponseDTO;
import com.payment.challange.data.models.ExtratoModel;
import com.payment.challange.domain.boundary.ExtratoBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtratoGateway implements ExtratoBoundary {
    private final ExtratoRepository extratoRepository;

    @Override
    public void processarExtrato(ExtratoModel extratoModel) {
        extratoRepository.processarExtrato(extratoModel);
    }

    @Override
    public SaldoResponseDTO obterSaldoDisponivel(String codigoMerchant) {
        return extratoRepository.obterSaldoDisponivel(codigoMerchant);
    }

    @Override
    public SaldoResponseDTO obterSaldoAguardandoFundos(String codigoMerchant) {
        return extratoRepository.obterSaldoAguardandoFundos(codigoMerchant);
    }
}
