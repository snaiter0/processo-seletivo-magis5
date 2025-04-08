package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(
        @JsonProperty(value = "volume_bebida_disponivel_venda_por_secao")
        List<VolumeBebidaDisponivelVendaPorSecaoDto> volumeBebidaDisponivelVendaPorSecaoDtos) {
}
