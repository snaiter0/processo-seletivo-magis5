package io.git.portal.logistica.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DisponibilidadeVendaEnum {
    DISPONIVEL,
    VENDIDO,
    INDISPONIVEL_TEMPORARIAMENTE,
    INDISPONIVEL;
}
