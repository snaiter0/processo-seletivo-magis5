package io.git.portal.logistica.application.dtos;

import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

public record ConsultarVolumeBebidaTotalPorTipoSaida(Double valorTotalLitragem,
                                                     TipoBebidaEnum tipoBebida) {
}
