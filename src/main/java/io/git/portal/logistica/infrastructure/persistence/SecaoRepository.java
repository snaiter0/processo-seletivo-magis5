package io.git.portal.logistica.infrastructure.persistence;

import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SecaoRepository extends JpaRepository<Secao, Long> {

    @Query(value = "SELECT s FROM Secao s " +
            "LEFT JOIN FETCH Bebida b " +
            "ON b.secao.id = s.id " +
            "where s.ultimoTipoBebidaArmazenado = :tipoBebida")
    Set<Secao> consultarSecoesFetchBebidasPorTipo(@Param("tipoBebida") TipoBebidaEnum tipoBebida);

    @Query(value = "SELECT s FROM Secao s " +
            "LEFT JOIN FETCH Bebida b " +
            "ON b.secao.id = s.id " +
            "where b.tipoBebida=:tipoBebida AND " +
            "b.disponivelVenda=:disponibilidadeVenda" )
    Set<Secao> consultarSecaoBebidasDisponiveisVendaPorTipo(@Param("tipoBebida") TipoBebidaEnum tipoBebida,
                                                            @Param("disponibilidadeVenda") DisponibilidadeVendaEnum disponibilidadeVenda);
}
