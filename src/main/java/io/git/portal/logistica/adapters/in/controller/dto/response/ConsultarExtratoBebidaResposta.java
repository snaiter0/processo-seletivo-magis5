package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;

import java.util.List;

public record ConsultarExtratoBebidaResposta(
        @JsonProperty(value = "extratos_bebida")
        List<ExtratoBebidaDto> extratosBebidaDtoSet) {
}
