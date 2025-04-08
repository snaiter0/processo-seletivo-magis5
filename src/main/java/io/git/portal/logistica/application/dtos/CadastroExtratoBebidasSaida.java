package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CadastroExtratoBebidasSaida(
        @JsonProperty(value = "extrato_bebida")
        ExtratoBebidaDto extratoBebidaDto) {
}
