package io.git.portal.logistica.adapters.out.persistence;

import io.git.portal.logistica.application.ports.out.ExtratoBebidaRepositoryPort;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.ExtratoBebidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ExtratoRepositoryAdapter implements ExtratoBebidaRepositoryPort {

    private final ExtratoBebidaRepository extratoBebidaRepository;

    @Override
    public ExtratoBebida save(ExtratoBebida extrato) {
        return extratoBebidaRepository.save(extrato);
    }

    @Override
    public Page<ExtratoBebida> consultarExtratosBebidaPorTipoBebidaESecao(TipoBebidaEnum tipoBebida, String localizacaoSecao, Pageable pageable) {
        return extratoBebidaRepository.consultarExtratosBebidaPorTipoBebidaESecao(tipoBebida, localizacaoSecao, pageable);
    }

    @Override
    public Optional<ExtratoBebida> consultarPorIdentificadorExterno(UUID idExterno) {
        return extratoBebidaRepository.consultarPorIdentificadorExterno(idExterno);
    }
}

