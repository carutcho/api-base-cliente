package br.com.eicon.api.consultacredito.api.v1.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String token;
    private long expiresIn;
}
