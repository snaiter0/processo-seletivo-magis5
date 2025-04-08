package io.git.portal.logistica.adapters.in.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.BebidaDto;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CadastroBebidasRequisicao(
        @JsonProperty(value = "bebidas")
        @NotNull
        Set<BebidaDto> bebidasDto,
        @JsonProperty(value = "id_externo")
        UUID idExterno) {
}
