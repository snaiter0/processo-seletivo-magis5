package io.git.portal.logistica.application.ports.out;

import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ExtratoBebidaRepositoryPort {
    ExtratoBebida save(ExtratoBebida extrato);

    Page<ExtratoBebida> consultarExtratosBebidaPorTipoBebidaESecao(TipoBebidaEnum tipoBebida, String localizacaoSecao, Pageable pageable);

    Optional<ExtratoBebida> consultarPorIdentificadorExterno(UUID idExterno);
}
