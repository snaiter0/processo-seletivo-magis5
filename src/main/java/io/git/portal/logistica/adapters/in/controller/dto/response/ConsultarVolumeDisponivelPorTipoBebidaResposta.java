package io.git.portal.logistica.adapters.in.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.application.dtos.VolumeDisponivelPorSecaoDto;

import java.util.List;

public record ConsultarVolumeDisponivelPorTipoBebidaResposta(
        @JsonProperty(value = "volume_disponivel_por_secao")
        List<VolumeDisponivelPorSecaoDto> volumeDisponivelPorSecaoDtoList) {
}
