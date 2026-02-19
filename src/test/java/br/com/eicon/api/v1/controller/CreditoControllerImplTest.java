package br.com.eicon.api.consultacredito.api.v1.controller;

import br.com.eicon.api.consultacredito.api.v1.controller.impl.CreditoControllerImpl;
import br.com.eicon.api.consultacredito.api.v1.dto.CreditoResponseDto;
import br.com.eicon.api.consultacredito.domain.service.CreditoService;
import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditoControllerImplTest {

    @Mock
    private CreditoService service;

    @Mock
    private ReloadableResourceBundleMessageSource messageSource;

    @InjectMocks
    private CreditoControllerImpl controller;

    private Credito sample;

    @BeforeEach
    void setup() {
        sample = Credito.builder()
                .numeroCredito("123456").numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024,2,25))
                .valorIssqn(new BigDecimal("1500.75")).tipoCredito("ISSQN")
                .simplesNacional(true).aliquota(new BigDecimal("5.0"))
                .valorFaturado(new BigDecimal("30000.00")).valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00")).build();
    }

    @Test
    void buscarPorNumero_sucesso_retornOK() {
        when(service.buscarPorNumeroCredito("123456")).thenReturn(sample);

        ResponseEntity<CreditoResponseDto> resp = controller.buscarPorNumero("123456");

        assertEquals(200, resp.getStatusCodeValue());
        CreditoResponseDto dto = resp.getBody();
        assertNotNull(dto);
        assertEquals("123456", dto.getNumeroCredito());
        verify(service).buscarPorNumeroCredito("123456");
    }


}