package br.com.builders.basecliente.api.v1.dto;


import br.com.builders.basecliente.jpa.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

    Long id;
    String cpf;
    String name;
    LocalDate birthDate;
    Integer age;

    public CustomerResponseDto (Customer customer){
        BeanUtils.copyProperties(customer, this);
    }
}
