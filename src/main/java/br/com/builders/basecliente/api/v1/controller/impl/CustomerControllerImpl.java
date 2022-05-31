package br.com.builders.basecliente.api.v1.controller.impl;


import br.com.builders.basecliente.api.v1.controller.CustomerController;
import br.com.builders.basecliente.api.v1.dto.CustomerRequestDto;
import br.com.builders.basecliente.api.v1.dto.CustomerResponseDto;
import br.com.builders.basecliente.domain.service.CustomerService;
import br.com.builders.basecliente.jpa.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/customers")
public class CustomerControllerImpl implements CustomerController {

    private CustomerService customerService;

    @Override
    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CustomerRequestDto customerRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        customerService.create(customer);

        CustomerResponseDto response = new CustomerResponseDto();
        BeanUtils.copyProperties(customer, response);
        return new ResponseEntity<CustomerResponseDto>(response, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{customerId}")
    public CustomerResponseDto findById(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId);
        CustomerResponseDto response = new CustomerResponseDto();
        BeanUtils.copyProperties(customer, response);
        return response;
    }


    @Override
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable Long customerId, @RequestBody CustomerRequestDto customerRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        customer.setId(customerId);
        customerService.update(customer);

        CustomerResponseDto response = new CustomerResponseDto();
        BeanUtils.copyProperties(customer, response);

        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public Page<CustomerResponseDto> fetchCustomersAsFilteredList(@RequestParam(defaultValue = "") String nameFilter
            , @RequestParam(defaultValue = "") String cpfFilter
            , @RequestParam(defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFilter
            , @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(defaultValue = "1") Integer size){

        Page<Customer> customers = customerService.fetchFilteredCustomerDataAsList(nameFilter,
                cpfFilter,
                birthDateFilter,
                page, size);

        return customers.map(obj -> new CustomerResponseDto(obj));
    }
}
