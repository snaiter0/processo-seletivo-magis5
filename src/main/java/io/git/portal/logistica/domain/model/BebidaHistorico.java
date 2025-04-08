package io.git.portal.logistica.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.git.portal.logistica.domain.model.base.AuditedModelBase;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
public class BebidaHistorico extends AuditedModelBase {

    @Column(name = "categoria_bebida", nullable = false)
    private String categoriaBebida;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "data_saida")
    private LocalDate dataSaida;

    @Column(name = "marca")
    private String marca;

    @Column(name = "tipo_bebida")
    @Enumerated(EnumType.STRING)
    private TipoBebidaEnum tipoBebida;

    @Column(name = "preco_compra")
    private Double precoCompra;

    @Column(name = "preco_medio")
    private Double precoMedio;

    @Column(name = "preco_venda")
    private Double precoVenda;

    @Column(name = "litragem")
    private Double litragem;

    @Enumerated(EnumType.STRING)
    @Column(name = "disp_venda")
    private DisponibilidadeVendaEnum disponivelVenda;

    @Column(name = "data_validade")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataValidade;

    @JoinColumn(name = "secao_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SecaoHistorico.class)
    private SecaoHistorico secao;
}
