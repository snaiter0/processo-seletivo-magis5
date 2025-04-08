package io.git.portal.logistica.domain.model;

import io.git.portal.logistica.domain.model.base.AuditedModelBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
public class SecaoHistorico extends AuditedModelBase {

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "capacidade_arm_alcool")
    private int capacidadeArmazenamentoAlcool;

    @Column(name = "capacidade_arm_nao_alcool")
    private int capacidadeArmazenamentoNaoAlcool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extrato_id")
    private ExtratoBebida extratoBebida;

    @OneToMany(mappedBy = "secao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BebidaHistorico> bebidas;
}
