package io.git.portal.logistica.application.ports.in;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaDisponivelPorTipoSaida;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaDisponivelVendaPorTipoSaida;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaTotalPorTipoSaida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

import java.util.List;

public interface SecaoService {
    ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum tipoBebida);

    ConsultarVolumeBebidaDisponivelPorTipoSaida consultarVolumeBebidaDisponivelPorTipo(Double espacoArmazenamentoNecessario, TipoBebidaEnum tipoBebida);

    ConsultarVolumeBebidaDisponivelVendaPorTipoSaida consultarVolumeBebidaDisponivelParaVendaPorTipo(Double volumeDesejado, TipoBebidaEnum tipoBebida);

    List<CadastroBebidasSaida> cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao);
}

