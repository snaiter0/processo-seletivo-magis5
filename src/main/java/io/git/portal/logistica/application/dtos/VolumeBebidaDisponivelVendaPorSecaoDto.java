package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record VolumeBebidaDisponivelVendaPorSecaoDto(@JsonProperty(value = "secao_id")
                                                     Long secaoId,
                                                     @JsonProperty(value = "localizacao_secao")
                                                     String localizacaoSecao,
                                                     @JsonProperty(value = "litragem_disponivel_venda")
                                                     Double litragemDisponivelVenda ) {
}
