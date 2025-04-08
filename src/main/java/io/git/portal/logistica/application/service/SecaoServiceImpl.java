package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.*;
import io.git.portal.logistica.application.ports.in.BebidaService;
import io.git.portal.logistica.application.ports.in.SecaoService;
import io.git.portal.logistica.application.ports.out.SecaoRepositoryPort;
import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.DisponibilidadeVendaEnum;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecaoServiceImpl implements SecaoService {
    private final SecaoRepositoryPort secaoRepository; // futuro port do secaoRepositoryAdapter;
    private final BebidaService bebidaService;


    @Override
    public ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum tipoBebida) {
        return bebidaService.consultarVolumeTotalPorTipoBebida(tipoBebida);
    }

    @Override
    public ConsultarVolumeBebidaDisponivelPorTipoSaida consultarVolumeBebidaDisponivelPorTipo(Double espacoArmazenamentoNecessario,
                                                                                              TipoBebidaEnum tipoBebida) {
       Set<Secao> secoes =  secaoRepository.consultarSecoesFetchBebidasPorTipo(tipoBebida);

        List<VolumeDisponivelPorSecaoDto> secoesDisponiveis = secoes
                .stream()
                .map(secao -> {
                    double litragemAtual = secao.getBebidas()
                            .stream()
                            .mapToDouble(Bebida::getLitragem)
                            .sum();

                    double litragemDisponivel = tipoBebida.getCapacidadeMaxima() - litragemAtual;

                    return new VolumeDisponivelPorSecaoDto(
                            secao.getId(),
                            secao.getLocalizacao(),
                            litragemDisponivel
                    );
                })
                .filter(dto -> dto.litragemDisponivel() >= espacoArmazenamentoNecessario)
                .toList();

        return new ConsultarVolumeBebidaDisponivelPorTipoSaida(secoesDisponiveis);
    }

    @Override
    public ConsultarVolumeBebidaDisponivelVendaPorTipoSaida consultarVolumeBebidaDisponivelParaVendaPorTipo(Double volumeDesejado, TipoBebidaEnum tipoBebida) {
        Set<Secao> secoes = secaoRepository.consultarSecaoBebidasDisponiveisVendaPorTipo(tipoBebida, DisponibilidadeVendaEnum.DISPONIVEL);

        return new ConsultarVolumeBebidaDisponivelVendaPorTipoSaida(secoes.stream()
                .filter(s ->{
                    return s.getBebidas()
                            .stream()
                            .mapToDouble(Bebida::getLitragem)
                            .sum() >= volumeDesejado;
                })
                .map(s->{
                    double litragemDisponivelVendaDaSecao;

                    litragemDisponivelVendaDaSecao =  s.getBebidas()
                            .stream()
                            .mapToDouble(Bebida::getLitragem)
                            .sum();

                    return new VolumeBebidaDisponivelVendaPorSecaoDto(s.getId(), s.getLocalizacao(), litragemDisponivelVendaDaSecao);
                }).toList());
    }

    @Override
    public List<CadastroBebidasSaida> cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao) {

        List<CadastroBebidasSaida> cadastrosBebidas = new ArrayList<CadastroBebidasSaida>();
        // 1. Agrupa bebidas por tipo
        Map<TipoBebidaEnum, Set<BebidaDto>> bebidasPorTipo = cadastroBebidasRequisicao.bebidasDto().stream()
                .collect(Collectors.groupingBy(
                        BebidaDto::tipoBebida,
                        Collectors.toSet()
                ));

        // 2. Itera sobre cada tipo de bebida para encontrar uma seção válida
        for (Map.Entry<TipoBebidaEnum, Set<BebidaDto>> entrada : bebidasPorTipo.entrySet()) {
            TipoBebidaEnum tipoBebida = entrada.getKey();
            Set<BebidaDto> bebidasDoTipo = entrada.getValue();

            Set<Secao> secoesDisponiveis = secaoRepository.consultarSecoesFetchBebidasPorTipo(tipoBebida);

            Secao secao = secoesDisponiveis.stream()
                    .filter(s -> s.isAdicionarNovasBebidas(bebidasDoTipo, tipoBebida))
                    .findFirst()
                    .orElseThrow(() -> new CadastrarBebidasException(
                            "Nao existe espaco de armazenamento suficiente para as bebidas do tipo: " + tipoBebida.name(),
                            cadastroBebidasRequisicao
                    ));

            // 3. Realiza o cadastro para o tipo de bebida
            cadastrosBebidas.add(bebidaService.cadastroBebidas(
                    new CadastroBebidasRequisicao(bebidasDoTipo, cadastroBebidasRequisicao.idExterno()), tipoBebida, secao));
        }
        return cadastrosBebidas;
    }

}
