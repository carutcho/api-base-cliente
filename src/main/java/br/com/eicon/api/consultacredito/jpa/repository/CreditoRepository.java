package br.com.eicon.api.consultacredito.jpa.repository;

import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    Collection<Credito> findByNumeroNfse(String numeroNfse);
    Optional<Credito> findByNumeroCredito(String numeroCredito);
    Collection<Credito>  findAllByOrderByDataConstituicaoDescTipoCreditoAsc();
}
