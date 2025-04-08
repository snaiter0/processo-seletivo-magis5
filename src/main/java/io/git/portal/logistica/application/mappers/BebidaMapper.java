package io.git.portal.logistica.application.mappers;

import io.git.portal.logistica.application.dtos.BebidaDto;
import io.git.portal.logistica.domain.model.Bebida;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BebidaMapper {

    List<Bebida> toBebidaEntity(Set<BebidaDto> bebidasDto);

    Bebida toBebidaEntity(BebidaDto bebidaDto);

    Set<BebidaDto> toBebidaDto(List<Bebida> bebidas);
}
