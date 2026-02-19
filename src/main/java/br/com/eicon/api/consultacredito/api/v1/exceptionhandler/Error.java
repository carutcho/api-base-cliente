package br.com.eicon.api.consultacredito.api.v1.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Error {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;

}
