package br.com.builders.basecliente.domain.service.impl;

import br.com.builders.basecliente.domain.exception.BusinessException;
import br.com.builders.basecliente.domain.exception.CodigoMensagem;
import br.com.builders.basecliente.domain.service.CustomerService;
import br.com.builders.basecliente.jpa.entity.Customer;
import br.com.builders.basecliente.jpa.repository.CustomerRepository;
import br.com.builders.basecliente.utils.ValidateCPF;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;


@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {

        validateRequired(customer);
        validateDocument(customer.getCpf());
        validateInsertExist(customer);

        customer.setStartDate(LocalDateTime.now());
        customer.setLastUpdateDate(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Collection<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Collection<Customer> find(Customer customer) {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer) {

        Customer customerDb = getCustomer(customer);

        validateRequiredUpdate(customer);
        validateDocument(customer.getCpf());
        validateDocumentUpdate(customer);

        customer.setStartDate(customerDb.getStartDate());
        customer.setLastUpdateDate(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(long id) {

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()){
            throw new BusinessException(CodigoMensagem.RN_004_CODIGO_NAO_ENCONTRADO);
        }
        return customer.get();
    }

    @Override
    public Page<Customer> fetchFilteredCustomerDataAsList(String name, String cpf, LocalDate birthDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findByNameLikeOrCpfEqualsOrBirthDateEquals(name, cpf, birthDate, pageable);
    }

    @Override
    public Customer findByCpf(String cpf) {
        validateDocument(cpf);
        Optional<Customer> customer = customerRepository.findByCpf(cpf);
        if (customer.isEmpty()){
            return null;
        }
        return customer.get();
    }

    @Override
    public void delete(long customerId) {
        try {
            customerRepository.deleteById(customerId);
        }catch (EmptyResultDataAccessException e){
            throw new BusinessException(CodigoMensagem.RN_004_CODIGO_NAO_ENCONTRADO);
        }
    }


    private void validateDocument(String cpf) {
        ValidateCPF validateCPF = new ValidateCPF();
        if(!validateCPF.isValid(cpf)){
            throw new BusinessException(CodigoMensagem.RN_002_DOCUMENTO_INVALIDO);
        }
    }

    private void validateRequiredUpdate(Customer customer){
        validateRequired(customer);
        if (customer.getId() <= 0){
            throw new BusinessException(CodigoMensagem.FG_001_ERRO_INESPERADO);
        }
    }

    private void validateRequired(Customer customer) {

        if (customer == null){
            throw new BusinessException(CodigoMensagem.FG_002_CORPO_REQUISICAO_INVALIDO);
        }

        if (ObjectUtils.isEmpty(customer.getName())){
            throw new BusinessException(CodigoMensagem.RN_CUSTOMER_001_NAME_VAZIO);
        }

        if (ObjectUtils.isEmpty(customer.getBirthDate())){
            throw new BusinessException(CodigoMensagem.RN_CUSTOMER_002_BIRTH_DATE_VAZIO);
        }

        if (ObjectUtils.isEmpty(customer.getCpf())){
            throw new BusinessException(CodigoMensagem.RN_001_DOCUMENTO_VAZIO);
        }
    }


    private Customer getCustomer(Customer customer) {
        Customer base = findById(customer.getId());
        return base;
    }

    private Customer getCustomerByCpf(Customer customer) {
        Customer base = findByCpf(customer.getCpf());
        if (ObjectUtils.isEmpty(base)){
            throw new BusinessException(CodigoMensagem.RN_004_CODIGO_NAO_ENCONTRADO);

        }
        return base;
    }

    private void validateInsertExist(Customer customer) {
        if (!ObjectUtils.isEmpty(findByCpf(customer.getCpf()))){
            throw new BusinessException(CodigoMensagem.RN_CUSTOMER_003_CUSTOMER_EXISTENTE_DOCUMENTO);
        }
    }


    private void validateDocumentUpdate(Customer customer) {
        Customer base = findByCpf(customer.getCpf());
        if (base != null && base.getId() != customer.getId()) {
            throw new BusinessException(CodigoMensagem.RN_CUSTOMER_003_CUSTOMER_EXISTENTE_DOCUMENTO);
        }
    }

}
