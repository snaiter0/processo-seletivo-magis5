package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

import java.util.Set;

public record CadastroBebidasSaida( @JsonProperty(value = "bebidas")
                                    Set<BebidaDto> bebidasDto,
                                    @JsonProperty(value = "tipo_bebida")
                                    TipoBebidaEnum tipoBebidaEnum) {
}
