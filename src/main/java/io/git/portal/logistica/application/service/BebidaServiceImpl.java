package io.git.portal.logistica.application.service;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import io.git.portal.logistica.application.dtos.CadastroBebidasSaida;
import io.git.portal.logistica.application.dtos.ConsultarVolumeBebidaTotalPorTipoSaida;
import io.git.portal.logistica.application.mappers.BebidaMapper;
import io.git.portal.logistica.application.ports.in.BebidaService;
import io.git.portal.logistica.application.ports.out.BebidaRepositoryPort;
import io.git.portal.logistica.domain.model.Bebida;
import io.git.portal.logistica.domain.model.Secao;
import io.git.portal.logistica.domain.model.enums.TipoBebidaEnum;
import io.git.portal.logistica.infrastructure.persistence.BebidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BebidaServiceImpl implements BebidaService {
    private final BebidaMapper bebidaMapper;
    private final BebidaRepositoryPort bebidaRepository;

    @Override
    public ConsultarVolumeBebidaTotalPorTipoSaida consultarVolumeTotalPorTipoBebida(TipoBebidaEnum tipoBebida) {
                return new ConsultarVolumeBebidaTotalPorTipoSaida(bebidaRepository.consultarbebidasPorTipo(tipoBebida)
                        .stream()
                        .mapToDouble(Bebida::getLitragem)
                        .sum(), tipoBebida);
    }

    @Override
    @Transactional
    public CadastroBebidasSaida cadastroBebidas(CadastroBebidasRequisicao cadastroBebidasRequisicao,
                                                TipoBebidaEnum tipoBebida,
                                                Secao secaoDisponivel) {
        List<Bebida> listaBebida = bebidaMapper.toBebidaEntity(cadastroBebidasRequisicao.bebidasDto())
                .stream()
                .peek(b -> b.setSecao(secaoDisponivel))
                .toList();

        return new CadastroBebidasSaida(
                bebidaMapper.toBebidaDto(bebidaRepository.saveAll(listaBebida)),
                tipoBebida);
    }
}
