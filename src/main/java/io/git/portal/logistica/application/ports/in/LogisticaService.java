package io.git.portal.logistica.application.ports.in;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LogisticaService {

    ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum TipoBebida);

    ConsultarVolumeBebidaDisponivelPorTipoSaida consultarVolumeBebidaDisponivelPorTipo(Double espacoArmazenamentoNecessario,
                                                                                       TipoBebidaEnum TipoBebida);

    ConsultarVolumeBebidaDisponivelVendaPorTipoSaida consultarVolumeDisponivelParaVendaPorTipo(Double volumeDesejado,
                                                                                               TipoBebidaEnum TipoBebida);

    CadastroExtratoBebidasSaida cadastrarExtratoBebida(CadastroExtratoBebidasRequisicao request);

    ConsultarExtratosBebidaSaida consultarExtratoBebidasPorSecaoETipo(TipoBebidaEnum TipoBebida,
                                                                      String localizacaoSessao,
                                                                      Pageable pageable);

    List<CadastroBebidasSaida> cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao);
}
