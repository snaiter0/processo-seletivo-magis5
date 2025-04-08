package io.git.portal.logistica.adapters.in.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.git.portal.logistica.adapters.in.controller.dto.mappers.LogisticaResponseMapper;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.response.*;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.application.ports.in.BebidaService;
import io.git.portal.logistica.application.ports.in.ExtratoBebidaService;
import io.git.portal.logistica.application.ports.in.LogisticaService;
import io.git.portal.logistica.application.ports.in.SecaoService;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(LogisticaController.class)
class LogisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogisticaService service;

    @MockBean
    private BebidaService bebidaService;

    @MockBean
    private ExtratoBebidaService extratoBebidaService;

    @MockBean
    private SecaoService secaoService;

    @MockBean
    private LogisticaResponseMapper responseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 201 ao cadastrar bebidas com sucesso")
    void deveRetornar201_quandoCadastroDeBebidasForBemSucedido() throws Exception {

        BebidaDto bebidaDto = BebidaDto.builder()
                .categoriaBebida("Cerveja")
                .litragem(500.0)
                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                .build();

        CadastroBebidasRequisicao requisicao = new CadastroBebidasRequisicao(Set.of(bebidaDto), UUID.randomUUID());

        Mockito.when(service.cadastroBebidas(requisicao)).thenReturn(List.of());
        Mockito.when(responseMapper.toCadastroBebidasResposta(Mockito.any())).thenReturn(new CadastroBebidasResposta(List.of()));

        mockMvc.perform(post("/logistica/api/v1/estoque/secao/bebida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar 400 quando requisição de cadastro de bebidas estiver inválida")
    void deveRetornar400_quandoRequisicaoDeCadastroDeBebidasEstiverInvalida() throws Exception {
        CadastroBebidasRequisicao requisicaoInvalida = null;

        mockMvc.perform(post("/logistica/api/v1/estoque/secao/bebida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicaoInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 500 quando serviço lançar exceção no cadastro de bebidas")
    void deveRetornar500_quandoServicoFalharNoCadastroDeBebidas() throws Exception {
        CadastroBebidasRequisicao requisicao = new CadastroBebidasRequisicao(new HashSet<>(), UUID.randomUUID());

        Mockito.when(service.cadastroBebidas(Mockito.any()))
                .thenThrow(new RuntimeException("Falha interna"));

        mockMvc.perform(post("/logistica/api/v1/estoque/secao/bebida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isInternalServerError());
    }



    @Test
    @DisplayName("Deve retornar 200 ao consultar volume total por tipo")
    void deveRetornar200_quandoConsultarVolumeTotalPorTipoBebida() throws Exception {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        var saida = new ConsultarVolumeBebidaTotalPorTipoSaida(1000.0, TipoBebidaEnum.ALCOOLICA);
        var resposta = new ConsultarVolumeTotalPorTipoBebidaResposta(1000.0, TipoBebidaEnum.ALCOOLICA);

        Mockito.when(service.consultarVolumeTotalPorTipoBebida(tipo)).thenReturn(saida);
        Mockito.when(responseMapper.toConsultarVolumeTotalPorTipoBebidaResposta(saida)).thenReturn(resposta);

        mockMvc.perform(get("/logistica/api/v1/estoque/secao/bebida/volume-total")
                        .param("tipoBebidaEnum", tipo.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar 400 quando tipo de bebida for inválido na consulta por tipo")
    void deveRetornar400_quandoTipoBebidaForInvalido_naConsultaPorTipo() throws Exception {
        mockMvc.perform(get("/logistica/api/v1/estoque/secao/bebida/volume-total")
                        .param("tipoBebidaEnum", "INVALIDO"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Deve retornar 200 ao consultar volume disponível por tipo valido")
    void deveRetornar200_quandoFornecidoTipoValidoNaConsultaVolumeDisponivel() throws Exception {
        double volume = 300.0;
        TipoBebidaEnum tipo = TipoBebidaEnum.NAO_ALCOOLICA;
        var saida = new ConsultarVolumeBebidaDisponivelPorTipoSaida(List.of(VolumeDisponivelPorSecaoDto.builder()
                .secaoId(1L)
                .localizacaoSecao("A1")
                .litragemDisponivel(1.5)
                .build()));

        var resposta = new ConsultarVolumeDisponivelPorTipoBebidaResposta(List.of(VolumeDisponivelPorSecaoDto.builder()
                .secaoId(1L)
                .localizacaoSecao("A1")
                .litragemDisponivel(1.5)
                .build()));

        Mockito.when(service.consultarVolumeBebidaDisponivelPorTipo(volume, tipo)).thenReturn(saida);
        Mockito.when(responseMapper.toConsultarVolumeBebidaDisponivelPorTipoResposta(saida)).thenReturn(resposta);

        mockMvc.perform(get("/logistica/api/v1/estoque/secao/bebida/volume-disponivel")
                        .param("espacoArmazenamentoNecessario", String.valueOf(volume))
                        .param("tipoBebida", tipo.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar 200 ao consultar seções disponíveis para venda")
    void deveRetornar200_quandoParametrosForemValidosNaConsultaDeVolumeDeBebidasDisponiveisParaVenda() throws Exception {
        double volume = 300.0;
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;

        var saida = new ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(List.of(VolumeBebidaDisponivelVendaPorSecaoDto.builder()
                        .litragemDisponivelVenda(1.5)
                        .secaoId(2L)
                        .localizacaoSecao("A2")
                .build()));

        var resposta = new ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(List.of(VolumeBebidaDisponivelVendaPorSecaoDto.builder()
                .litragemDisponivelVenda(1.5)
                .secaoId(2L)
                .localizacaoSecao("A2")
                .build()));

        Mockito.when(service.consultarVolumeDisponivelParaVendaPorTipo(volume, tipo)).thenReturn(saida);
        Mockito.when(responseMapper.toConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(saida)).thenReturn(resposta);

        mockMvc.perform(get("/logistica/api/v1/estoque/secao/bebida/volume-venda")
                        .param("volumeDesejado", String.valueOf(volume))
                        .param("tipoBebida", tipo.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar 204 ao consultar seções disponíveis para venda e nao houver disponibilidade.")
    void deveRetornar204_quandoParametrosForemValidosNaConsultaDeVolumeDeBebidasDisponiveisParaVendaMasNaoHouverDisponibilidade() throws Exception {
        double volumeDesejado = 700;
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;

        var saida = new ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(List.of());

        Mockito.when(service.consultarVolumeDisponivelParaVendaPorTipo(volumeDesejado, tipo)).thenReturn(new ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(List.of()));
        Mockito.when(responseMapper.toConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(saida)).thenReturn(new ConsultarSecoesBebidaDisponiveisVendaPorTipoResposta(List.of()));

        mockMvc.perform(get("/logistica/api/v1/estoque/secao/bebida/volume-venda")
                        .param("volumeDesejado", String.valueOf(volumeDesejado))
                        .param("tipoBebida", tipo.name()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("Deve retornar 201 ao cadastrar extrato de bebidas")
    void deveRetornar201_quandoCadastroExtratoDeBebidasForBemSucedido() throws Exception {
        CadastroExtratoBebidasRequisicao requisicao = new CadastroExtratoBebidasRequisicao(ExtratoBebidaDto.builder()
                .dataRegistroExtrato(LocalDateTime.now())
                .secoesHistorico(List.of(SecaoHistoricoDto.builder()
                        .capacidadeArmazenamentoAlcool(400)
                        .capacidadeArmazenamentoNaoAlcool(500)
                        .localizacao("A1")
                        .bebidas(List.of(BebidaHistoricoDto.builder()
                                .categoriaBebida("Cerveja")
                                .litragem(2.0)
                                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                                .build()))
                .build())).build());

        var saida = new CadastroExtratoBebidasSaida(ExtratoBebidaDto.builder()
                .dataRegistroExtrato(LocalDateTime.now())
                .secoesHistorico(List.of(SecaoHistoricoDto.builder()
                        .capacidadeArmazenamentoAlcool(400)
                        .capacidadeArmazenamentoNaoAlcool(500)
                        .localizacao("A1")
                        .bebidas(List.of(BebidaHistoricoDto.builder()
                                .categoriaBebida("Cerveja")
                                .litragem(2.0)
                                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                                .build()))
                        .build())).build()); // Idem
        var resposta = new CadastroExtratoBebidasResposta(ExtratoBebidaDto.builder()
                .dataRegistroExtrato(LocalDateTime.now())
                .secoesHistorico(List.of(SecaoHistoricoDto.builder()
                        .capacidadeArmazenamentoAlcool(400)
                        .capacidadeArmazenamentoNaoAlcool(500)
                        .localizacao("A1")
                        .bebidas(List.of(BebidaHistoricoDto.builder()
                                .categoriaBebida("Cerveja")
                                .litragem(2.0)
                                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                                .build()))
                        .build())).build());

        Mockito.when(service.cadastrarExtratoBebida(requisicao)).thenReturn(saida);
        Mockito.when(responseMapper.toCadastroExtratoBebidasResponse(saida)).thenReturn(resposta);

        log.info("JSON de resposta: {}", resposta);

        mockMvc.perform(post("/logistica/api/v1/estoque/extrato")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 200 ao consultar extrato por seção e tipo")
    void deveRetornar200_quandoConsultarExtratoPorSecaoETipoBebida() throws Exception {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        String localizacao = "A1";

        var saida = new ConsultarExtratosBebidaSaida(List.of(ExtratoBebidaDto.builder()
                .dataRegistroExtrato(LocalDateTime.now())
                .secoesHistorico(List.of(SecaoHistoricoDto.builder()
                        .capacidadeArmazenamentoAlcool(400)
                        .capacidadeArmazenamentoNaoAlcool(500)
                        .localizacao("A1")
                        .bebidas(List.of(BebidaHistoricoDto.builder()
                                .categoriaBebida("Cerveja")
                                .litragem(2.0)
                                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                                .build()))
                        .build())).build()));

        var resposta = new ConsultarExtratoBebidaResposta(List.of(ExtratoBebidaDto.builder()
                .dataRegistroExtrato(LocalDateTime.now())
                .secoesHistorico(List.of(SecaoHistoricoDto.builder()
                        .capacidadeArmazenamentoAlcool(400)
                        .capacidadeArmazenamentoNaoAlcool(500)
                        .localizacao("A1")
                        .bebidas(List.of(BebidaHistoricoDto.builder()
                                .categoriaBebida("Cerveja")
                                .litragem(2.0)
                                .tipoBebida(TipoBebidaEnum.ALCOOLICA)
                                .build()))
                        .build())).build()));

        ExtratoBebida mockExtrato = Mockito.mock(ExtratoBebida.class);
        Page<ExtratoBebida> pageFake = new PageImpl<>(List.of(mockExtrato));

        Mockito.when(service.consultarExtratoBebidasPorSecaoETipo(tipo, localizacao, Pageable.ofSize(10))).thenReturn(saida);
        Mockito.when(responseMapper.toConsultarExtratoBebidaResposta(saida)).thenReturn(resposta);

        mockMvc.perform(get("/logistica/api/v1/estoque/extrato/secao-historico")
                        .param("tipoBebida", tipo.name())
                        .param("localizacaoSecao", localizacao))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
