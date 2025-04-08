package io.git.portal.logistica.application.mappers;

import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SecaoHistoricoMapper.class})
public interface ExtratoBebidaMapper {

    ExtratoBebidaDto toExtratoBebidaDto(ExtratoBebida extratoBebida);

    @Mapping(target = "extratoBebidaDto", source = "extratoBebida")
    List<ExtratoBebidaDto> toExtratoBebidaDtoLista(List<ExtratoBebida> extratoBebida);

}
