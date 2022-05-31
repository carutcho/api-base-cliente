package br.com.builders.basecliente.domain.service;

import br.com.builders.basecliente.jpa.entity.Customer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Collection;

public interface CustomerService {

     Customer create(Customer cliente);

     Collection<Customer> list();

     Collection<Customer> find(Customer customer);

     Customer findByCpf(String cpf);

     Customer update(Customer cliente);

     Customer findById(long id);

     Page<Customer> fetchFilteredCustomerDataAsList(String name, String cpf, LocalDate birthDate, int page, int size);

     void delete(long customerId);
}

