package com.payment.challange.data.mapper;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.models.ExtratoModel;
import org.mapstruct.Mapper;

@Mapper
public interface ExtratoMapper {
    ExtratoModel transacaoDtoToExtratoModel(TransacaoRequestDTO transacaoRequestDTO);
}
