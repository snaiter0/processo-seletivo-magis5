package io.git.portal.logistica.application.ports.out;

import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

import java.util.Set;

public interface SecaoRepositoryPort {
    Set<Secao> consultarSecoesFetchBebidasPorTipo(TipoBebidaEnum tipoBebida);

    Set<Secao> consultarSecaoBebidasDisponiveisVendaPorTipo(TipoBebidaEnum tipoBebida, DisponibilidadeVendaEnum disponibilidadeVendaEnum);
}
