package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;
import lombok.Builder;

@Builder
public record CadastroExtratoBebidasResposta(
        @JsonProperty(value = "extrato_bebida")
        ExtratoBebidaDto extratoBebidaDto) {
}
