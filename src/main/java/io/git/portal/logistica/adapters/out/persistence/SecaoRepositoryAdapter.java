package io.git.portal.logistica.adapters.out.persistence;

import io.git.portal.logistica.application.ports.out.SecaoRepositoryPort;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.SecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@RequiredArgsConstructor
@Repository
public class SecaoRepositoryAdapter implements SecaoRepositoryPort {

    private final SecaoRepository secaoRepository;


    @Override
    public Set<Secao> consultarSecoesFetchBebidasPorTipo(TipoBebidaEnum tipoBebida) {
        return secaoRepository.consultarSecoesFetchBebidasPorTipo(tipoBebida);
    }

    @Override
    public Set<Secao> consultarSecaoBebidasDisponiveisVendaPorTipo(TipoBebidaEnum tipoBebida, DisponibilidadeVendaEnum disponibilidadeVendaEnum) {
        return secaoRepository.consultarSecaoBebidasDisponiveisVendaPorTipo(tipoBebida, disponibilidadeVendaEnum);
    }
}
