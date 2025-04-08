package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.application.ports.in.ExtratoBebidaService;
import io.git.portal.logistica.application.ports.in.LogisticaService;
import io.git.portal.logistica.application.ports.in.SecaoService;
import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.domain.model.enums.TipoExtratoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogisticaServiceImpl implements LogisticaService {

    private final SecaoService secaoService;
    private final ExtratoBebidaService extratoService;


    @Override
    public ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum tipoBebida) {
        return secaoService.consultarVolumeTotalPorTipoBebida(tipoBebida);
    }

    @Override
    public ConsultarVolumeBebidaDisponivelPorTipoSaida consultarVolumeBebidaDisponivelPorTipo(Double espacoArmazenamentoNecessario, TipoBebidaEnum tipoBebida) {
        return secaoService.consultarVolumeBebidaDisponivelPorTipo(espacoArmazenamentoNecessario, tipoBebida);
    }

    @Override
    public ConsultarVolumeBebidaDisponivelVendaPorTipoSaida consultarVolumeDisponivelParaVendaPorTipo(Double volumeDesejado, TipoBebidaEnum tipoBebida) {
        return secaoService.consultarVolumeBebidaDisponivelParaVendaPorTipo(volumeDesejado, tipoBebida);
    }

    @Override
    public CadastroExtratoBebidasSaida cadastrarExtratoBebida(CadastroExtratoBebidasRequisicao requisicao) {
        return extratoService.cadastrarExtratoBebidas(requisicao);
    }

    @Override
    public ConsultarExtratosBebidaSaida consultarExtratoBebidasPorSecaoETipo(TipoBebidaEnum tipoBebida, String localizacaoSecao, Pageable pageable) {
        return extratoService.consultarExtratoBebidasPorSecaoETipo(tipoBebida, localizacaoSecao, pageable);
    }

    @Override
    public List<CadastroBebidasSaida> cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao) {
        ExtratoBebida extrato = extratoService.consultarExtratosBebidaSaidaPorIdExterno(cadastroBebidasRequisicao.idExterno());

       if(extrato.confereBebidasAdicionadas(cadastroBebidasRequisicao.bebidasDto()) && extrato.isExtratoEntrada()){
           return secaoService.cadastroBebidas(cadastroBebidasRequisicao);
       }else{
           throw new CadastrarBebidasException("Falha ao cadastrar novas bebidas, o extrato informado nao possui as mesmas " +
                   "bebidas do registro ou não é um extrato de entrada. ", cadastroBebidasRequisicao);
       }

    }
}
