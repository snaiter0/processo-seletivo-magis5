package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

public record ConsultarVolumeTotalPorTipoBebidaResposta(
        @JsonProperty(value = "valor_total_litragem")
        Double valorTotalLitragem,
        @JsonProperty(value = "tipo_bebida")
        TipoBebidaEnum tipoBebida) {
}
