package io.git.portal.logistica.adapters.in.controller;

import io.git.portal.logistica.adapters.in.controller.dto.mappers.LogisticaResponseMapper;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.response.*;
import io.git.portal.logistica.adapters.in.controller.dto.routes.Rotas;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaDisponivelVendaPorTipoSaida;
import io.git.portal.logistica.application.ports.in.LogisticaService;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Rotas.API_LOGISTICA)
@RequiredArgsConstructor
@Validated
public class LogisticaController {

    private final LogisticaService service;
    private final LogisticaResponseMapper logisticaResponseMapper;

    @Operation(
            summary = "Cadastra uma lista de bebidas.",
            description = "Cadastra uma lista de bebidas alcoolicas ou nao alcoolicas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarVolumeTotalPorTipoBebidaResposta.class)))
    })
    @PostMapping(value = Rotas.Bebida.BEBIDA, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastrarBebidasResposta> cadastrarBebidas(@RequestBody @NotNull CadastroBebidasRequisicao cadastroBebidasRequisicao){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                logisticaResponseMapper.toCadastroBebidasResposta(
                    service.cadastroBebidas(cadastroBebidasRequisicao)));
    }

    @Operation(
            summary = "Consulta volume total de bebidas por tipo",
            description = "Consulta o volume total de bebidas no estoque, classificadas por tipo: alcoólica ou não alcoólica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso, há volume disponível.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarVolumeTotalPorTipoBebidaResposta.class))),
            @ApiResponse(responseCode = "204", description = "Consulta realizada com sucesso, não há volume disponivel.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarVolumeTotalPorTipoBebidaResposta.class)))
    })
    @GetMapping(value = Rotas.Bebida.VOLUME_TOTAL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultarVolumeTotalPorTipoBebidaResposta> buscarVolumeTotalPorTipo(@RequestParam TipoBebidaEnum tipoBebidaEnum){
        ConsultarVolumeTotalPorTipoBebidaResposta saida = logisticaResponseMapper.toConsultarVolumeTotalPorTipoBebidaResposta(
                service.consultarVolumeTotalPorTipoBebida(tipoBebidaEnum));

            return saida.valorTotalLitragem() == 0 ?
                    montarResposta(HttpStatus.NO_CONTENT, saida) : montarResposta(HttpStatus.OK, saida);
    }

    @Operation(
            summary = "Consulta volume disponível de bebidas por tipo",
            description = "Verifica se há espaço suficiente para armazenar bebidas a partir de determinado volume necessário e o tipo de bebida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso, correspodencias encontradas.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarVolumeDisponivelPorTipoBebidaResposta.class))),
            @ApiResponse(responseCode = "204", description = "Consulta realizada com sucesso, nao houveram correspondencias",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarVolumeDisponivelPorTipoBebidaResposta.class)))
    })
    @GetMapping(value = Rotas.Bebida.VOLUME_DISPONIVEL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultarVolumeDisponivelPorTipoBebidaResposta> buscarVolumeDisponivelPorTipo(@RequestParam Double espacoArmazenamentoNecessario,
                                                                                                        @RequestParam TipoBebidaEnum tipoBebida){

        ConsultarVolumeDisponivelPorTipoBebidaResposta saida = logisticaResponseMapper.toConsultarVolumeBebidaDisponivelPorTipoResposta(
                service.consultarVolumeBebidaDisponivelPorTipo(espacoArmazenamentoNecessario, tipoBebida));

        return saida.volumeDisponivelPorSecaoDtoList().isEmpty() ?
                montarResposta(HttpStatus.NO_CONTENT, saida) : montarResposta(HttpStatus.OK, saida);
    }

    @Operation(
            summary = "Consulta seções com volume disponível para venda por tipo de bebida",
            description = "Informa quais seções possuem estoque disponível para venda de um volume específico de determinado tipo de bebida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso, recursos encontrados.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta.class))),
            @ApiResponse(responseCode = "204", description = "Consulta realizada com sucesso, nao foram encontrados recursos.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta.class)))
    })
    @GetMapping(value = Rotas.Bebida.VOLUME_PARA_VENDA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultarVolumeBebidaDisponivelVendaPorTipoSaida> buscarSecoesComVolumeDisponivelParaVenda(@RequestParam Double volumeDesejado,
                                                                                                                     @RequestParam TipoBebidaEnum tipoBebida){
        ConsultarVolumeBebidaDisponivelVendaPorTipoSaida saida =
                service.consultarVolumeDisponivelParaVendaPorTipo(volumeDesejado, tipoBebida);

        return saida.volumeBebidaDisponivelVendaPorSecaoDtos().isEmpty() ?
                montarResposta(HttpStatus.NO_CONTENT, saida) : montarResposta(HttpStatus.OK, saida);
    }

    @Operation(
            summary = "Registrar extrato de bebidas",
            description = "Cadastra um novo extrato contendo o histórico de entrada/saida de bebidas em seções do estoque."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato cadastrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CadastrarExtratoBebidasResposta.class)))
    })
    @PostMapping(value = Rotas.Extrato.EXTRATO)
    public ResponseEntity<CadastrarExtratoBebidasResposta> cadastrarExtratoBebida(@RequestBody @NotNull CadastroExtratoBebidasRequisicao request){
        return montarResposta(HttpStatus.CREATED, logisticaResponseMapper.toCadastroExtratoBebidasResponse(
                service.cadastrarExtratoBebida(request)));
    }

    @Operation(
            summary = "Consultar extrato de bebidas por seção e tipo",
            description = "Consulta o extrato histórico de bebidas armazenadas em uma seção específica, filtrando por tipo de bebida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsultarExtratoBebidaResposta.class)))
    })
    @GetMapping(value = Rotas.Extrato.EXTRATO_BEBIDAS_POR_SESSAO_POR_TIPO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultarExtratoBebidaResposta> buscarExtratoPorSecaoETipo(@RequestParam TipoBebidaEnum tipoBebida,
                                                                                     @RequestParam String localizacaoSecao,
                                                                                     Pageable pageable){
        return montarResposta(HttpStatus.OK, logisticaResponseMapper.toConsultarExtratoBebidaResposta(
                service.consultarExtratoBebidasPorSecaoETipo(tipoBebida, localizacaoSecao, pageable)));
    }

    private <T> ResponseEntity<T> montarResposta(HttpStatus status, T body) {
        if (body == null) {
            return ResponseEntity.status(status).build();
        }
        return ResponseEntity.status(status).body(body);
    }
}
