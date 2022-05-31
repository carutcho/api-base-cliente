package br.com.builders.basecliente.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;


class ValidationUtilsTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    @Description("Validando remover mascara")
    public void removerMascaraCPFeCNPJ(){
        String CNPJ_COM_MASCARA = "93.234.175/0001-44";
        String CNPJ_SEM_MASCARA = "93234175000144";
        Assertions.assertEquals (ValidationUtils.removerMascaraCPF(CNPJ_COM_MASCARA), CNPJ_SEM_MASCARA);
    }

    @Test
    public void validarDocumentoVazio(){
        String CNPJ_COM_MASCARA = "";
        String CNPJ_SEM_MASCARA = "";
        Assertions.assertEquals (ValidationUtils.removerMascaraCPF(CNPJ_COM_MASCARA), CNPJ_SEM_MASCARA);
    }

    @Test
    public void validarDocumentoNulo(){
        String CNPJ_COM_MASCARA = null;
        String CNPJ_SEM_MASCARA = null;
        Assertions.assertEquals (ValidationUtils.removerMascaraCPF(CNPJ_COM_MASCARA), CNPJ_SEM_MASCARA);
    }

    @Test
    public void validarDocumentoSemMascara(){
        String CNPJ_COM_MASCARA = "93234175000144";
        String CNPJ_SEM_MASCARA = "93234175000144";
        Assertions.assertEquals (ValidationUtils.removerMascaraCPF(CNPJ_COM_MASCARA), CNPJ_SEM_MASCARA);
    }
}