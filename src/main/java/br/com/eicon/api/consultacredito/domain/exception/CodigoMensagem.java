// br/com/eicon/api/consultacredito/domain/exception/CodigoMensagem.java
package br.com.eicon.api.consultacredito.domain.exception;

import lombok.Getter;

@Getter
public enum CodigoMensagem {

    // ===== Erros - Falhas genéricas =======
    FG_001_ERRO_INESPERADO("ERRO.FG.001_ERRO_INESPERADO"),
    FG_002_CORPO_REQUISICAO_INVALIDO("ERRO.FG.002_CORPO_REQUISICAO_INVALIDO"),
    FG_003_PARAMETRO_INVALIDO("ERRO.FG.003_PARAMETRO_INVALIDO"),
    FG_004_RECURSO_INEXISTENTE("ERRO.FG.004_RECURSO_INEXISTENTE"),
    FG_005_PROPRIEDADE_INEXISTENTE("ERRO.FG.005_PROPRIEDADE_INEXISTENTE"),

    // ===== Erros - Regras de Negócio para Crédito =======
    RN_CREDITO_001_NUMERO_NFSE_NAO_INFORMADO("ERRO.RN.CREDITO_001_NUMERO_NFSE_NAO_INFORMADO"),
    RN_CREDITO_002_NUMERO_CREDITO_NAO_INFORMADO("ERRO.RN.CREDITO_002_NUMERO_CREDITO_NAO_INFORMADO"),
    RN_CREDITO_NAO_ENCONTRADO("ERRO.RN.CREDITO_NAO_ENCONTRADO"),

    // ===== Erros - Regras de Negócio para API Key =======
    RN_API_KEY_NAO_ENCONTRADA("ERRO.RN.API_KEY_NAO_ENCONTRADA"),
    RN_API_KEY_JA_REVOGADA("ERRO.RN.API_KEY_JA_REVOGADA");

    private final String value;

    CodigoMensagem(String value) {
        this.value = value;
    }
}
