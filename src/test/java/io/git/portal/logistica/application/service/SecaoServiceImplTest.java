package io.git.portal.logistica.application.service;


import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.application.ports.in.BebidaService;
import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.SecaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecaoServiceImplTest {


    @Mock
    private SecaoRepository secaoRepository;

    @Mock
    private BebidaService bebidaService;

    @InjectMocks
    private SecaoServiceImpl secaoService;

    @Mock
    private Secao secao;

    private Bebida bebida;

    @BeforeEach
    void setUp() {
        bebida = Bebida.builder().litragem(3.0).build();
    }


    @Test
    void deveConsultarVolumeTotalPorTipoBebidaDelegandoParaBebidaService() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        ConsultarVolumeBebidaTotalPorTipoSaida esperado = new ConsultarVolumeBebidaTotalPorTipoSaida(10.0, tipo);
        when(bebidaService.consultarVolumeTotalPorTipoBebida(tipo)).thenReturn(esperado);

        var resultado = secaoService.consultarVolumeTotalPorTipoBebida(tipo);

        assertEquals(esperado, resultado);
        verify(bebidaService).consultarVolumeTotalPorTipoBebida(tipo);
    }

    @Test
    void deveConsultarVolumeBebidaDisponivelPorTipo() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        bebida.setTipoBebida(tipo);
        secao.setBebidas(List.of(bebida));
        when(secaoRepository.consultarSecoesFetchBebidasPorTipo(tipo)).thenReturn(Set.of(secao));

        var resultado = secaoService.consultarVolumeBebidaDisponivelPorTipo(1.0, tipo);

        assertEquals(1, resultado.volumeDisponivelPorSecaoDtoList().size());
        VolumeDisponivelPorSecaoDto dto = resultado.volumeDisponivelPorSecaoDtoList().get(0);
        assertEquals(secao.getId(), dto.secaoId());
        assertTrue(dto.litragemDisponivel() > 0);
    }

    @Test
    void deveConsultarVolumeDisponivelVendaPorTipo() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        bebida.setDisponivelVenda(DisponibilidadeVendaEnum.DISPONIVEL);
        bebida.setTipoBebida(tipo);

        when(secao.getId()).thenReturn(1L);
        when(secao.getBebidas()).thenReturn(List.of(bebida));

        when(secaoRepository.consultarSecaoBebidasDisponiveisVendaPorTipo(tipo, DisponibilidadeVendaEnum.DISPONIVEL))
                .thenReturn(Set.of(secao));

        var resultado = secaoService.consultarVolumeBebidaDisponivelParaVendaPorTipo(2.0, tipo);

        assertEquals(1, resultado.volumeBebidaDisponivelVendaPorSecaoDtos().size());
        VolumeBebidaDisponivelVendaPorSecaoDto dto = resultado.volumeBebidaDisponivelVendaPorSecaoDtos().get(0);
        assertEquals(secao.getId(), dto.secaoId());
        assertTrue(dto.litragemDisponivelVenda() >= 3.0);
    }


    @Test
    void deveCadastrarBebidasComSucesso() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;

        BebidaDto bebidaDto = BebidaDto.builder()
                .litragem(5.0)
                .tipoBebida(tipo).build();

        Set<BebidaDto> bebidas = Set.of(bebidaDto);

        CadastroBebidasRequisicao requisicao = new CadastroBebidasRequisicao(bebidas, UUID.randomUUID());

        when(secaoRepository.consultarSecoesFetchBebidasPorTipo(tipo)).thenReturn(Set.of(secao));
        when(secao.isAdicionarNovasBebidas(anySet(), eq(tipo))).thenReturn(true);
        when(bebidaService.cadastroBebidas(any(), eq(tipo), eq(secao)))
                .thenReturn(new CadastroBebidasSaida(Set.of(bebidaDto), tipo));

        var resultado = secaoService.cadastroBebidas(requisicao);

        assertEquals(1, resultado.size());
        assertEquals(tipo, resultado.get(0).tipoBebidaEnum());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverEspacoParaCadastro() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;

        BebidaDto bebidaDto = BebidaDto.builder()
                .litragem(10.0)
                .tipoBebida(tipo).build();

        Set<BebidaDto> bebidas = Set.of(bebidaDto);
        CadastroBebidasRequisicao requisicao = new CadastroBebidasRequisicao(bebidas, UUID.randomUUID());

        when(secaoRepository.consultarSecoesFetchBebidasPorTipo(tipo)).thenReturn(Set.of(secao));
        when(secao.isAdicionarNovasBebidas(anySet(), eq(tipo))).thenReturn(false);

        CadastrarBebidasException ex = assertThrows(CadastrarBebidasException.class,
                () -> secaoService.cadastroBebidas(requisicao));

        assertTrue(ex.getMessage().contains("Nao existe espaco de armazenamento suficiente"));
    }

}
