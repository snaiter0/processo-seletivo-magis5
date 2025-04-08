package io.git.portal.logistica.domain.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditedModelBase extends ModelBase {

    @Column(name = "dat_insercao_reg", updatable = false)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @CreatedDate
    protected LocalDateTime dataHoraInclusaoRegistro;

    @Column(name = "dat_hora_ult_modif_reg")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @LastModifiedDate
    protected LocalDateTime dataHoraUltimaModificacaoRegistro;
}
