package io.git.portal.logistica.infrastructure.persistence;

import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BebidaRepository extends JpaRepository<Bebida, Long> {

    @Query(value = "SELECT b FROM Bebida b " +
            "where b.tipoBebida = :tipoBebida")
    Set<Bebida> consultarbebidasPorTipo(@Param("tipoBebida") TipoBebidaEnum tipoBebida);

    @Query(value = "SELECT b FROM Bebida b " +
            "where b.tipoBebida = :tipoBebida AND " +
            "b.disponivelVenda = :disponibilidade")
    Set<Bebida> consultarbebidasPorTipoEDisponibilidade(@Param("tipoBebida") TipoBebidaEnum tipoBebida,
                                                        @Param("disponibilidade") DisponibilidadeVendaEnum disponibilidade);

}
