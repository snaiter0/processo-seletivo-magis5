package io.git.portal.logistica.adapters.out.persistence;

import io.git.portal.logistica.application.ports.out.BebidaRepositoryPort;
import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.BebidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class BebidaRepositoryAdapter implements BebidaRepositoryPort {

    private final BebidaRepository repository;

    @Override
    public List<Bebida> saveAll(List<Bebida> listaBebida) {
        return repository.saveAll(listaBebida);
    }

    @Override
    public Set<Bebida> consultarbebidasPorTipo(TipoBebidaEnum tipoBebida) {
        return repository.consultarbebidasPorTipo(tipoBebida);
    }
}
