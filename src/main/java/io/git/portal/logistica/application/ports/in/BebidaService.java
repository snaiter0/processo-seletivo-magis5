package io.git.portal.logistica.application.ports.in;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaTotalPorTipoSaida;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;

public interface BebidaService {
    ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum tipoBebida);

    CadastroBebidasSaida cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao, TipoBebidaEnum tipoBebida, Secao secaoDisponivel);
}
