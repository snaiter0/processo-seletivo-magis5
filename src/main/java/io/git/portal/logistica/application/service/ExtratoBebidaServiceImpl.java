package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroExtratoBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroExtratoBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarExtratosBebidaSaida;
import io.git.portal.logistica.application.dtos.ExtratoBebidaDto;
import io.git.portal.logistica.application.mappers.ExtratoBebidaMapper;
import io.git.portal.logistica.application.mappers.SecaoHistoricoMapper;
import io.git.portal.logistica.application.ports.in.ExtratoBebidaService;
import io.git.portal.logistica.application.ports.out.ExtratoBebidaRepositoryPort;
import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.model.BebidaHistorico;
import io.git.portal.logistica.domain.model.ExtratoBebida;
import io.git.portal.logistica.domain.model.SecaoHistorico;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtratoBebidaServiceImpl implements ExtratoBebidaService {

    private final ExtratoBebidaMapper extratoBebidaMapper;
    private final SecaoHistoricoMapper secaoHistoricoMapper;
    private final ExtratoBebidaRepositoryPort extratoBebidaRepository;

    @Override
    @Transactional
    public CadastroExtratoBebidasSaida cadastrarExtratoBebidas(CadastroExtratoBebidasRequisicao requisicao) {

        ExtratoBebida extrato = ExtratoBebida.builder()
                .dataRegistroExtrato(requisicao.extratoBebida().dataRegistroExtrato() != null ? requisicao.extratoBebida().dataRegistroExtrato() : LocalDateTime.now())
                .tipoExtratoEnum(requisicao.extratoBebida().tipoExtratoEnum())
                .secoesHistorico(secaoHistoricoMapper.toEntity(requisicao.extratoBebida().secoesHistorico()))
                .build();

        // vincular o pai nas secoes e o pai nas bebidas para o cascade.all persistir as FK.
        for (SecaoHistorico secao : extrato.getSecoesHistorico()) {
            secao.setExtratoBebida(extrato);

            if (secao.getBebidas() != null) {
                for (BebidaHistorico bebida : secao.getBebidas()) {
                    bebida.setSecao(secao);
                }
            }
        }

        return new CadastroExtratoBebidasSaida(
                extratoBebidaMapper.toExtratoBebidaDto(
                        extratoBebidaRepository.save(extrato)));
    }

    @Override
    public ConsultarExtratosBebidaSaida consultarExtratoBebidasPorSecaoETipo(TipoBebidaEnum tipoBebida, String localizacaoSecao, Pageable pageable) {
        List<ExtratoBebida> extratosBebidas = extratoBebidaRepository.consultarExtratosBebidaPorTipoBebidaESecao(tipoBebida,
                localizacaoSecao,
                pageable).stream().toList();

                if(extratosBebidas.isEmpty()){
                    log.info("Nao existem extratos com os parametros fornecidos. ");
                }

        List<ExtratoBebidaDto> extratosBebidasDtoLista = extratoBebidaMapper.toExtratoBebidaDtoLista(extratosBebidas);
        return new ConsultarExtratosBebidaSaida(extratosBebidasDtoLista) ;
    }

    @Override
    public ExtratoBebida consultarExtratosBebidaSaidaPorIdExterno(UUID idExterno) {
        return extratoBebidaRepository.consultarPorIdentificadorExterno(idExterno).orElseThrow(() ->
                new CadastrarBebidasException("Falha ao cadastrar lista de novas bebidas, o extrato de entrada" +
                "das bebidas ainda não foi encontrado, verifique o identificador externo: " + idExterno));
    }
}
