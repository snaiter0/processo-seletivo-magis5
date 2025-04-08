package io.git.portal.logistica.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.git.portal.logistica.application.dtos.BebidaDto;
import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.model.base.AuditedModelBase;
import io.git.portal.logistica.domain.model.enums.TipoExtratoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "extrato_bebida")
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoBebida extends AuditedModelBase {

    @Column(name = "id_externo", nullable = false, unique = true)
    private UUID idExterno;

    @Column(name="tipo_extrato_enum")
    @Enumerated(EnumType.STRING)
    private TipoExtratoEnum tipoExtratoEnum;

    @Column(name = "data_registro_extrato")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime dataRegistroExtrato;

    @OneToMany(targetEntity = SecaoHistorico.class, mappedBy = "extratoBebida", cascade = CascadeType.ALL)
    private List<SecaoHistorico> secoesHistorico;

    @PrePersist
    public void prePersist(){
        if(this.idExterno==null){
            this.idExterno = UUID.randomUUID();
        }
    }

    public boolean isExtratoEntrada(){
        return this.getTipoExtratoEnum().equals(TipoExtratoEnum.ENTRADA);
    }

    public boolean isExtratoSaida(){
        return this.getTipoExtratoEnum().equals(TipoExtratoEnum.SAIDA);
    }

    public boolean confereBebidasAdicionadas(Set<BebidaDto> novasBebidas) {
        Set<String> novas = novasBebidas.stream()
                .map(b -> b.marca() + "|" + b.tipoBebida() + "|" + b.litragem())
                .collect(Collectors.toSet());

        Set<String> extratoBebidas = secoesHistorico.stream()
                .flatMap(secao -> secao.getBebidas().stream())
                .map(b -> b.getMarca() + "|" + b.getTipoBebida() + "|" + b.getLitragem())
                .collect(Collectors.toSet());

        return novas.equals(extratoBebidas);
    }
}
