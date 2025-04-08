package io.git.portal.logistica.application.dtos;

import java.util.List;

public record ConsultarVolumeBebidaDisponivelPorTipoSaida(
        List<VolumeDisponivelPorSecaoDto> volumeDisponivelPorSecaoDtoList) {
}
