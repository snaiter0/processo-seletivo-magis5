package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.CadastroBebidasSaida;

import java.util.List;

public record CadastrarBebidasResposta(@JsonProperty(value = "cadastros_bebidas")
                                      List<CadastroBebidasSaida> cadastrosBebidasSaidas) {
}
