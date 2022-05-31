package br.com.builders.basecliente.services.config;

import br.com.builders.basecliente.jpa.entity.Customer;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GeradorCustomerFake {

    public static String CPF_INVALIDO_MENOS_CARACTERES = "123456";
    public static String CPF_INVALIDO_CALCULO = "12345678901";
    public static String CPF_VALIDO_MASCARA = "709.153.680-05";
    public static String CPF_VALIDO_SEM_MASCARA = "70915368005";


    public Customer getValidCustomer(){

        LocalDate birthDate = LocalDate.parse("1983-10-19");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Reinaldo Torres");
        customer.setBirthDate(birthDate);
        customer.setCpf(CPF_VALIDO_SEM_MASCARA);

        return customer;

    }

}
