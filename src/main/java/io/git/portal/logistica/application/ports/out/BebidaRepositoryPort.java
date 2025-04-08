package io.git.portal.logistica.application.ports.out;

import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

import java.util.List;
import java.util.Set;

public interface BebidaRepositoryPort {
    List<Bebida> saveAll(List<Bebida> listaBebida);

    Set<Bebida> consultarbebidasPorTipo(TipoBebidaEnum tipoBebida);
}
