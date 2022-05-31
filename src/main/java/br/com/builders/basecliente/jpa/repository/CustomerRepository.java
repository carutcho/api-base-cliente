package br.com.builders.basecliente.jpa.repository;

import br.com.builders.basecliente.jpa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCpf(String cpf);

    String FILTER_CUSTOMERS_ON_NAME_OR_CPF_OR_BIRTH_DATE_QUERY = "select b from Customer b where UPPER(b.name) like CONCAT('%',UPPER(?1),'%') or b.cpf = ?2 or b.birthDate = ?3";

    @Query(FILTER_CUSTOMERS_ON_NAME_OR_CPF_OR_BIRTH_DATE_QUERY)
    Collection<Customer> findByNameLikeOrCpfEqualsOrBirthDateEquals(String name, String cpf, LocalDate birthDate);

    @Query(FILTER_CUSTOMERS_ON_NAME_OR_CPF_OR_BIRTH_DATE_QUERY)
    Page<Customer> findByNameLikeOrCpfEqualsOrBirthDateEquals(String name, String cpf, LocalDate birthDate, Pageable pageable);
}
