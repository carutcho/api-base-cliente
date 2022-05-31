package br.com.builders.basecliente.api.v1.dto;


import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
public class CustomerFilterDto {

    Long id;
    String cpf;
    String name;
    LocalDate birthDate;
}
