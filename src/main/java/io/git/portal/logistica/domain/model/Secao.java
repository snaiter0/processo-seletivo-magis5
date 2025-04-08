package io.git.portal.logistica.domain.model;

import io.git.portal.logistica.application.dtos.BebidaDto;
import io.git.portal.logistica.domain.model.base.ModelBase;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "secao", schema = "mercearia_liquida")
@Data
public class Secao extends ModelBase {

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "ultimo_tipo_bebida_armazenado")
    @Enumerated(EnumType.STRING)
    private TipoBebidaEnum ultimoTipoBebidaArmazenado;

    @Column(name = "data_esvaziamento_secao")
    private LocalDate dataEsvaziamentoSecao;

    @OneToMany(mappedBy = "secao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bebida> bebidas;

    @Column(name = "capacidade_arm_alcool")
    private int capacidadeArmazenamentoAlcool;

    @Column(name = "capacidade_arm_nao_alcool")
    private int capacidadeArmazenamentoNaoAlcool;


    public Boolean isMesmoTipoBebida(TipoBebidaEnum tipoBebida){
        return this.ultimoTipoBebidaArmazenado.equals(tipoBebida);
    }

    public Boolean isEspacoDisponivel(Set<BebidaDto> bebidasNovas){
        int capacidadeMaxima = TipoBebidaEnum.ALCOOLICA.equals(this.ultimoTipoBebidaArmazenado)
                ? this.capacidadeArmazenamentoAlcool
                : this.capacidadeArmazenamentoNaoAlcool;

        double totalLitragem = bebidasNovas.stream()
                .mapToDouble(BebidaDto::litragem)
                .sum() + this.bebidas.stream()
                .mapToDouble(Bebida::getLitragem)
                .sum();

        return totalLitragem <= capacidadeMaxima;
    }

    public Boolean isMudancaDeTipoDeBebidaPorSecao(TipoBebidaEnum tipoBebidaNovo){
        if (!this.ultimoTipoBebidaArmazenado.equals(tipoBebidaNovo)
                && tipoBebidaNovo.equals(TipoBebidaEnum.NAO_ALCOOLICA)
                && this.ultimoTipoBebidaArmazenado.equals(TipoBebidaEnum.ALCOOLICA)) {

            return this.dataEsvaziamentoSecao != null &&
                    LocalDate.now().isAfter(this.dataEsvaziamentoSecao.plusDays(1));
        }
        return true;
    }

    public Boolean isAdicionarNovasBebidas(Set<BebidaDto> bebidasNovas, TipoBebidaEnum tipoBebida){
        boolean mesmoTipo = isMesmoTipoBebida(tipoBebida);
        boolean temEspaco = isEspacoDisponivel(bebidasNovas);
        boolean podeTrocarTipo = isMudancaDeTipoDeBebidaPorSecao(tipoBebida);

        return (mesmoTipo && temEspaco) || (podeTrocarTipo && temEspaco);
    }
}
