package br.com.eicon.api.consultacredito.domain.service;

import br.com.eicon.api.consultacredito.jpa.entity.Credito;

import java.util.Collection;
import java.util.List;

public interface CreditoService {
    Collection<Credito> buscarPorNumeroNfse(String numeroNfse);
    Credito buscarPorNumeroCredito(String numeroCredito);
    Collection<Credito> buscarTodas();
}
