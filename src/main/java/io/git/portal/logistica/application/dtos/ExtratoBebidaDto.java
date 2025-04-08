package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.domain.model.enums.TipoExtratoEnum;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ExtratoBebidaDto(
                                @JsonProperty(value = "id_externo")
                                UUID idExterno,
                                @JsonProperty(value = "tipo_extrato")
                                TipoExtratoEnum tipoExtratoEnum,
                                @JsonProperty(value = "data_registro_extrato")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
                                LocalDateTime dataRegistroExtrato,
                                @JsonProperty(value = "secoes")
                                List<SecaoHistoricoDto> secoesHistorico) {
}