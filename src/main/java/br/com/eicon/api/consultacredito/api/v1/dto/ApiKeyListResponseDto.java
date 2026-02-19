package br.com.eicon.api.consultacredito.api.v1.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiKeyListResponseDto {

    private Long id;
    private String name;
    private boolean active;
    private LocalDateTime createdAt;
}
