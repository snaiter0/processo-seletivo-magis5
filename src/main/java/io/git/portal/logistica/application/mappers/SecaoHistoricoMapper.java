package io.git.portal.logistica.application.mappers;


import io.git.portal.logistica.application.dtos.BebidaHistoricoDto;
import io.git.portal.logistica.application.dtos.SecaoHistoricoDto;
import io.git.portal.logistica.domain.model.BebidaHistorico;
import io.git.portal.logistica.domain.model.SecaoHistorico;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SecaoHistoricoMapper {

    SecaoHistorico toEntity(SecaoHistoricoDto dto);

    SecaoHistoricoDto toDto(SecaoHistorico entity);

    List<SecaoHistorico> toEntity(List<SecaoHistoricoDto> entity);

    BebidaHistorico toBebidaHistoricoEntity (BebidaHistoricoDto dto);

    List<BebidaHistorico> toBebidaHistoricoEntity(List<BebidaHistoricoDto> dtos);

}
