package br.com.builders.basecliente.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRequisicaoDto {

    private String idRequisicao;
    private long tempoResposta;
    private String documento;


}
