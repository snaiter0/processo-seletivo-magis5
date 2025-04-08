package io.git.portal.logistica.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record BebidaDto(@NotBlank
                        @JsonProperty(value = "categoria_bebida")
                        String categoriaBebida,
                        @JsonProperty(value = "data_entrada")
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                        LocalDate dataEntrada,
                        @JsonProperty(value = "data_saida")
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                        LocalDate dataSaida,
                        @NotBlank
                        @JsonProperty(value = "marca")
                        String marca,
                        @NotNull
                        @JsonProperty(value = "tipo_bebida")
                        TipoBebidaEnum tipoBebida,
                        @JsonProperty(value = "preco_compra")
                        Double precoCompra,
                        @JsonProperty(value = "preco_medio")
                        Double precoMedio,
                        @JsonProperty(value = "preco_venda")
                        Double precoVenda,
                        @NotBlank
                        @JsonProperty(value = "litragem")
                        Double litragem,
                        @JsonProperty(value = "disponivel_venda")
                        @NotBlank
                        DisponibilidadeVendaEnum disponivelVenda,
                        @NotBlank
                        @JsonProperty(value = "data_validade")
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                        LocalDate dataValidade) {
}
