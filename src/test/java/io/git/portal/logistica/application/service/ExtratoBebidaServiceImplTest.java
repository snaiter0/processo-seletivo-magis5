package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroExtratoBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarExtratosBebidaSaida;
import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;
import io.git.portal.logistica.application.mappers.ExtratoBebidaMapper;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.ExtratoBebidaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExtratoBebidaServiceImplTest {

    @MockBean
    private ExtratoBebidaMapper extratoBebidaMapper;

    @MockBean
    private ExtratoBebidaRepository extratoBebidaRepository;

    @Autowired
    private ExtratoBebidaServiceImpl service;

    @Test
    void deveCadastrarExtratoBebidasComSucesso() {
        CadastroExtratoBebidasRequisicao requisicao = new CadastroExtratoBebidasRequisicao(ExtratoBebidaDto.builder()
                .secoesHistorico(List.of())
                .dataRegistroExtrato(LocalDateTime.now())
                .build());
        ExtratoBebida extrato = mock(ExtratoBebida.class);
        when(extratoBebidaRepository.save(any())).thenReturn(extrato);
        when(extratoBebidaMapper.toExtratoBebidaDto(extrato)).thenReturn(mock());

        CadastroExtratoBebidasSaida saida = service.cadastrarExtratoBebidas(requisicao);

        assertNotNull(saida);
        verify(extratoBebidaRepository).save(any());
        verify(extratoBebidaMapper).toExtratoBebidaDto(extrato);
    }

    @Test
    void deveConsultarExtratoBebidasPorSecaoETipo() {
        TipoBebidaEnum tipo = TipoBebidaEnum.NAO_ALCOOLICA;
        String localizacao = "A1";
        List<ExtratoBebida> extratos = List.of(mock(ExtratoBebida.class));

        when(extratoBebidaRepository.consultarExtratosBebidaPorTipoBebidaESecao(tipo, localizacao, any())).thenReturn((Page<ExtratoBebida>) extratos);
        when(extratoBebidaMapper.toExtratoBebidaDtoLista(extratos)).thenReturn(mock());

        ConsultarExtratosBebidaSaida saida = service.consultarExtratoBebidasPorSecaoETipo(tipo, localizacao, any());

        assertNotNull(saida);
        verify(extratoBebidaRepository).consultarExtratosBebidaPorTipoBebidaESecao(tipo, localizacao, any());
        verify(extratoBebidaMapper).toExtratoBebidaDtoLista(extratos);
    }
}
