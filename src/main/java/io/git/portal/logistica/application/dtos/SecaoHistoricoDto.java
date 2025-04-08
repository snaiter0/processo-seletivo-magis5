package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record SecaoHistoricoDto(
                                @JsonProperty(value="id")
                                Long id,
                                @JsonProperty(value = "localizacao")
                                String localizacao,
                                @JsonProperty(value = "capacidade_max_armazenamento_alcoolico")
                                int capacidadeArmazenamentoAlcool,
                                @JsonProperty(value = "capacidade_max_armazenamento_nao_alcoolico")
                                int capacidadeArmazenamentoNaoAlcool,
                                @JsonProperty(value = "bebidas")
                                List<BebidaHistoricoDto> bebidas) {
}
