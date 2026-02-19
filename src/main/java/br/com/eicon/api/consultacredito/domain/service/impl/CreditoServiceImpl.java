package br.com.eicon.api.consultacredito.domain.service.impl;

import br.com.eicon.api.consultacredito.domain.exception.BusinessException;
import br.com.eicon.api.consultacredito.domain.exception.CodigoMensagem;
import br.com.eicon.api.consultacredito.domain.service.CreditoService;
import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import br.com.eicon.api.consultacredito.jpa.repository.CreditoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CreditoServiceImpl implements CreditoService {

    private final CreditoRepository creditoRepository;

    @Override
    public Collection<Credito> buscarPorNumeroNfse(String numeroNfse) {

        return creditoRepository.findByNumeroNfse(numeroNfse);
    }

    @Override
    public Credito buscarPorNumeroCredito(String numeroCredito) {

        return creditoRepository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new BusinessException(CodigoMensagem.RN_CREDITO_NAO_ENCONTRADO));
    }

    @Override
    public Collection<Credito> buscarTodas() {
        return creditoRepository.findAllByOrderByDataConstituicaoDescTipoCreditoAsc();
    }
}