package br.com.eicon.api.consultacredito.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiKeyCreateRequestDto {

    @NotBlank
    private String name;
}
