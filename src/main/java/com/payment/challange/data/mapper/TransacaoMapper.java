package com.payment.challange.data.mapper;

import com.payment.challange.data.dtos.request.TransacaoRequestDTO;
import com.payment.challange.data.dtos.response.TransacaoResponseDTO;
import com.payment.challange.data.models.TransacaoModel;
import org.mapstruct.Mapper;

@Mapper
public interface TransacaoMapper {
    TransacaoModel dtoToModel(TransacaoRequestDTO dto);

    TransacaoResponseDTO modelToDtoResponse(TransacaoModel model);

}
