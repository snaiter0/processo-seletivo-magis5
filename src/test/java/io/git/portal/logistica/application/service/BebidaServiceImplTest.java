package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.BebidaDto;
import io.git.portal.logistica.application.mappers.BebidaMapper;
import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.BebidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BebidaServiceImplTest {

    @Mock
    private BebidaMapper bebidaMapper;

    @Mock
    private BebidaRepository bebidaRepository;

    @InjectMocks
    private BebidaServiceImpl bebidaService;

    @Test
    void deveConsultarVolumeTotalPorTipoBebida() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        List<Bebida> bebidas = List.of(
                Bebida.builder().categoriaBebida("cerveja").litragem(5.0).marca("Brahma").build(),
                Bebida.builder().categoriaBebida("suco").litragem(3.0).marca("Aurora").build()
        );

        when(bebidaRepository.consultarbebidasPorTipo(tipo)).thenReturn(new HashSet<>(bebidas));

        var saida = bebidaService.consultarVolumeTotalPorTipoBebida(tipo);

        assertEquals(8.0, saida.valorTotalLitragem());
        assertEquals(tipo, saida.tipoBebida());

        verify(bebidaRepository).consultarbebidasPorTipo(tipo);
    }

    @Test
    void deveCadastrarBebidasComSucesso() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        Secao secao = Secao.builder().localizacao("A1").build();
        secao.setId(1l);
        var bebidaDto = BebidaDto.builder().litragem(2.5).tipoBebida(tipo).categoriaBebida("refrigerante").marca("pepsi").build();
        var bebidaEntity = Bebida.builder().litragem(2.5).categoriaBebida("refrigerante").marca("pepsi").build();
        var bebidaEntityComSecao = Bebida.builder().litragem(2.5).secao(secao).categoriaBebida("refrigerante").marca("pepsi").build();

        CadastroBebidasRequisicao requisicao = new CadastroBebidasRequisicao(Set.of(bebidaDto), UUID.randomUUID());

        when(bebidaMapper.toBebidaEntity(requisicao.bebidasDto())).thenReturn(List.of(bebidaEntity));
        when(bebidaRepository.saveAll(List.of(bebidaEntityComSecao))).thenReturn(List.of(bebidaEntityComSecao));
        when(bebidaMapper.toBebidaDto(List.of(bebidaEntityComSecao))).thenReturn(Set.of(bebidaDto));

        var saida = bebidaService.cadastroBebidas(requisicao, tipo, secao);

        assertEquals(tipo, saida.tipoBebidaEnum());
        assertEquals(1, saida.bebidasDto().size());
        assertEquals(2.5, saida.bebidasDto().stream().toList().getFirst().litragem());

        verify(bebidaRepository).saveAll(anyList());
        verify(bebidaMapper).toBebidaEntity(anySet());
        verify(bebidaMapper).toBebidaDto(anyList());
    }
}
