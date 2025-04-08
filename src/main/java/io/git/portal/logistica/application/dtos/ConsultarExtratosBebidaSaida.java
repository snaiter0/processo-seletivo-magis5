package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ConsultarExtratosBebidaSaida(
        @JsonProperty(value = "extratos_bebida")
        List<ExtratoBebidaDto> extratosBebidaDtoSet) {
}
