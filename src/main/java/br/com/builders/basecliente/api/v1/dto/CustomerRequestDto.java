package br.com.builders.basecliente.api.v1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CustomerRequestDto {

    String cpf;
    String name;
    LocalDate birthDate;
}
