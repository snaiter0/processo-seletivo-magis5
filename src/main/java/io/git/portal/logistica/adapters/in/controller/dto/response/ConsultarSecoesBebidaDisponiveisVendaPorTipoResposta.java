package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.VolumeBebidaDisponivelVendaPorSecaoDto;

import java.util.List;

public record ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(
        @JsonProperty(value = "volume_bebida_disp_venda_secao_resposta")
        List<VolumeBebidaDisponivelVendaPorSecaoDto> volumeBebidaDisponivelVendaPorSecaoResposta) {
}
