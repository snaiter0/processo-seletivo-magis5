package io.git.portal.logistica.application.ports.in;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroExtratoBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarExtratosBebidaSaida;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ExtratoBebidaService {

    CadastroExtratoBebidasSaida cadastrarExtratoBebidas(CadastroExtratoBebidasRequisicao requisicao);

    ConsultarExtratosBebidaSaida consultarExtratoBebidasPorSecaoETipo(TipoBebidaEnum tipoBebida,
                                                                      String localizacaoSecao,
                                                                      Pageable pageable);

    ExtratoBebida consultarExtratosBebidaSaidaPorIdExterno(UUID idExterno);
}
