package br.com.eicon.api.consultacredito.domain.service;

import br.com.eicon.api.consultacredito.domain.exception.BusinessException;
import br.com.eicon.api.consultacredito.domain.exception.CodigoMensagem;
import br.com.eicon.api.consultacredito.domain.service.impl.CreditoServiceImpl;
import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import br.com.eicon.api.consultacredito.jpa.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceImplTest {

    @Mock
    private CreditoRepository creditoRepository;

    @InjectMocks
    private CreditoServiceImpl service;

    private Credito sample;

    @BeforeEach
    void setup() {
        sample = Credito.builder()
                .id(1L)
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024,2,25))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.0"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();
    }

    @Test
    void buscarPorNumeroNfse_valido_deveRetornarLista() {
        when(creditoRepository.findByNumeroNfse("7891011"))
                .thenReturn(List.of(sample));

        List<Credito> result = (List<Credito>) service.buscarPorNumeroNfse("7891011");

        assertEquals(1, result.size());
        assertEquals(sample, result.get(0));
        verify(creditoRepository).findByNumeroNfse("7891011");
    }

    @Test
    void buscarPorNumeroCredito_valido_deveRetornarCredito() {
        when(creditoRepository.findByNumeroCredito("123456"))
                .thenReturn(Optional.of(sample));

        Credito result = service.buscarPorNumeroCredito("123456");

        assertEquals(sample, result);
        verify(creditoRepository).findByNumeroCredito("123456");
    }

    @Test
    void buscarPorNumeroCredito_emBranco_deveLancarBusinessException() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.buscarPorNumeroCredito(null));
        assertEquals(CodigoMensagem.RN_CREDITO_NAO_ENCONTRADO, ex.getCodigoMensagem());
    }

    @Test
    void buscarPorNumeroCredito_naoEncontrado_deveLancarBusinessException() {
        when(creditoRepository.findByNumeroCredito("000000"))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.buscarPorNumeroCredito("000000"));
        assertEquals(CodigoMensagem.RN_CREDITO_NAO_ENCONTRADO, ex.getCodigoMensagem());
    }
}