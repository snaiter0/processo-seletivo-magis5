package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.application.ports.in.ExtratoBebidaService;
import io.git.portal.logistica.application.ports.in.LogisticaService;
import io.git.portal.logistica.application.ports.in.SecaoService;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LogisticaServiceImplTest {

    @MockBean
    private SecaoService secaoService;

    @Autowired
    private LogisticaService logisticaService;

    @MockBean
    private ExtratoBebidaService extratoBebidaService;

    @Test
    void deveConsultarVolumeTotalPorTipoBebidaChamandoSecaoService() {
        when(secaoService.consultarVolumeTotalPorTipoBebida(TipoBebidaEnum.NAO_ALCOOLICA))
                .thenReturn(new ConsultarVolumeBebidaTotalPorTipoSaida(10.0, TipoBebidaEnum.NAO_ALCOOLICA));

        var resposta = logisticaService.consultarVolumeTotalPorTipoBebida(TipoBebidaEnum.NAO_ALCOOLICA);

        assertEquals(10.0, resposta.valorTotalLitragem());
        verify(secaoService).consultarVolumeTotalPorTipoBebida(TipoBebidaEnum.NAO_ALCOOLICA);
    }
    @Test
    void deveConsultarVolumeBebidaDisponivelPorTipo() {
        double espaco = 50.0;
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        ConsultarVolumeBebidaDisponivelPorTipoSaida saida = new ConsultarVolumeBebidaDisponivelPorTipoSaida(Collections.emptyList());
        when(secaoService.consultarVolumeBebidaDisponivelPorTipo(espaco, tipo)).thenReturn(saida);

        var result = logisticaService.consultarVolumeBebidaDisponivelPorTipo(espaco, tipo);

        assertEquals(saida, result);
        verify(secaoService).consultarVolumeBebidaDisponivelPorTipo(espaco, tipo);
    }

    @Test
    void deveConsultarVolumeDisponivelParaVendaPorTipo() {
        double volume = 20.0;
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        var saida = new ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(Collections.emptyList());
        when(secaoService.consultarVolumeBebidaDisponivelParaVendaPorTipo(volume, tipo)).thenReturn(saida);

        var result = logisticaService.consultarVolumeDisponivelParaVendaPorTipo(volume, tipo);

        assertEquals(saida, result);
        verify(secaoService).consultarVolumeBebidaDisponivelParaVendaPorTipo(volume, tipo);
    }

    @Test
    void deveCadastrarExtratoBebida() {
        CadastroExtratoBebidasRequisicao req = mock(CadastroExtratoBebidasRequisicao.class);
        CadastroExtratoBebidasSaida saida = mock(CadastroExtratoBebidasSaida.class);
        when(extratoBebidaService.cadastrarExtratoBebidas(req)).thenReturn(saida);

        var result = logisticaService.cadastrarExtratoBebida(req);

        assertEquals(saida, result);
        verify(extratoBebidaService).cadastrarExtratoBebidas(req);
    }

    @Test
    void deveConsultarExtratoBebidasPorSecaoETipo() {
        TipoBebidaEnum tipo = TipoBebidaEnum.ALCOOLICA;
        String localizacao = "A1";
        var saida = mock(ConsultarExtratosBebidaSaida.class);
        when(extratoBebidaService.consultarExtratoBebidasPorSecaoETipo(tipo, localizacao, any())).thenReturn(saida);

        var result = logisticaService.consultarExtratoBebidasPorSecaoETipo(tipo, localizacao, any());

        assertEquals(saida, result);
        verify(extratoBebidaService).consultarExtratoBebidasPorSecaoETipo(tipo, localizacao, any());
    }

    @Test
    void deveCadastrarBebidas() {
        CadastroBebidasRequisicao requisicao = mock(CadastroBebidasRequisicao.class);
        List<CadastroBebidasSaida> saidas = Collections.singletonList(mock(CadastroBebidasSaida.class));
        when(secaoService.cadastroBebidas(requisicao)).thenReturn(saidas);

        var result = logisticaService.cadastroBebidas(requisicao);

        assertEquals(saidas, result);
        verify(secaoService).cadastroBebidas(requisicao);
    }

}
