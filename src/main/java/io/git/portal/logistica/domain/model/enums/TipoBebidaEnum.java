package io.git.portal.logistica.domain.model.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoBebidaEnum {
    ALCOOLICA(500),
    NAO_ALCOOLICA(400);

    private final int capacidadeMaxima;
}
