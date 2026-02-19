package br.com.eicon.api.consultacredito.api.v1.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiKeyCreateResponseDto {

    private Long id;
    private String name;
    private String keyValue;
    private LocalDateTime createdAt;
    private String aviso;
}
