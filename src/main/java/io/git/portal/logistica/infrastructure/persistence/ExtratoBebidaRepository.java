package io.git.portal.logistica.infrastructure.persistence;

import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ExtratoBebidaRepository extends JpaRepository<ExtratoBebida, Long> {

    @Query(value = """
                    SELECT eb FROM ExtratoBebida eb
                    LEFT JOIN FETCH SecaoHistorico sh
                    on eb.id =sh.extratoBebida.id
                    LEFT JOIN FETCH BebidaHistorico bh
                    ON sh.id = bh.secao.id
                    WHERE sh.localizacao=:localizacao AND
                    bh.tipoBebida=:tipoBebida
""")
    Page<ExtratoBebida> consultarExtratosBebidaPorTipoBebidaESecao(@Param("tipoBebida") TipoBebidaEnum tipoBebida,
                                                                   @Param("localizacao") String localizacao,
                                                                   Pageable pageable);

    @Query(value = """
                    SELECT eb FROM ExtratoBebida eb
                    LEFT JOIN FETCH SecaoHistorico sh ON eb.id = sh.extratoBebida.id
                    LEFT JOIN FETCH BebidaHistorico bh ON sh.id = bh.secao.id
                    WHERE eb.idExterno=:idExterno
""")
    Optional<ExtratoBebida> consultarPorIdentificadorExterno(@Param("idExterno") UUID idExterno);
}
