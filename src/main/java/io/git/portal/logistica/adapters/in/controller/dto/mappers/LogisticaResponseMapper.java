package io.git.portal.logistica.adapters.in.controller.dto.mappers;

import io.git.portal.logistica.adapters.in.controller.dto.response.*;
import io.git.portal.logistica.application.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LogisticaResponseMapper {

    ConsultarExtratoBebidaResposta toConsultarExtratoBebidaResposta(ConsultarExtratosBebidaSaida consultarExtratosBebidaSaida);

    default CadastroBebidasResposta toCadastroBebidasResposta(List<CadastroBebidasSaida> cadastroBebidasSaida){
        return new CadastroBebidasResposta(cadastroBebidasSaida);
    }

    ConsultarVolumeTotalPorTipoBebidaResposta toConsultarVolumeTotalPorTipoBebidaResposta(ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeBebidaTotalPorTipoSaida);

    ConsultarVolumeDisponivelPorTipoBebidaResposta toConsultarVolumeBebidaDisponivelPorTipoResposta(ConsultarVolumeBebidaDisponivelPorTipoSaida consultarVolumeBebidaDisponivelPorTipoSaida);

    @Mapping(target = "volumeBebidaDisponivelVendaPorSecaoResposta", source = "volumeBebidaDisponivelVendaPorSecaoDtos")
    ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta toConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(ConsultarVolumeBebidaDisponivelVendaPorTipoSaida consultarVolumeBebidaDisponivelVendaPorTipoSaida);

    CadastroExtratoBebidasResposta toCadastroExtratoBebidasResponse(CadastroExtratoBebidasSaida cadastroExtratoBebidasSaida);
}
