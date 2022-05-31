package br.com.builders.basecliente.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

class ValidarCPFTest {

    private ValidateCPF validarCPF;

    @BeforeEach
    public void setUp() {
        validarCPF = new ValidateCPF();
    }

    @Test
    @Description("Validando um CPF verdadeiro")
    public void validarCpfVerdadeiro(){
        String CPF_OK = "28621836874";
        Assertions.assertTrue (validarCPF.isValid(CPF_OK));
    }

    @Test
    @Description("Validando um CPF false")
    public void validarCpfInvalido(){
        String CPF_ERRO = "28621836870";
        Assertions.assertFalse (validarCPF.isValid(CPF_ERRO));
    }

    @Test
    public void validarQuandoTamanhoValorDoDocumentoMenor11Digitos(){
        String valorDocumento = "7990599907";
        boolean resultado = validarCPF.isValid(valorDocumento);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void validarQuandoTamanhoValorDoDocumentoMaior11Digitos(){
        String valorDocumento = "799059990271";
        boolean resultado = validarCPF.isValid(valorDocumento);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void validarQuandoValorDocumentoForNulo(){
        String valorDocumento = null;
        boolean resultado = validarCPF.isValid(valorDocumento);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void validarQuandoValorDocumentoFor0(){
        String valorDocumento = "0";
        boolean resultado = validarCPF.isValid(valorDocumento);
        Assertions.assertFalse(resultado);
    }


}