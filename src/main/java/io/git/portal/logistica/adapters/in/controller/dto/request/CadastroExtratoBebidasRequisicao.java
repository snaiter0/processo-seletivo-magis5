package io.git.portal.logistica.adapters.in.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;

public record CadastroExtratoBebidasRequisicao(
        @JsonProperty(value = "extrato_bebida")
        ExtratoBebidaDto extratoBebida) {
}
