package br.com.eicon.api.consultacredito.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private CodigoMensagem codigoMensagem;
    private Object[] parametros;

    public BusinessException(CodigoMensagem codigoMensagem, Object...parametros) {
        this.codigoMensagem = codigoMensagem;
        this.parametros = parametros;
    }

    public BusinessException(CodigoMensagem codigoMensagem, Throwable causa, Object...parametros) {
        super(causa);
        this.codigoMensagem = codigoMensagem;
        this.parametros = parametros;
    }

}
