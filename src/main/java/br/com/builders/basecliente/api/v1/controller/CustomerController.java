package br.com.builders.basecliente.api.v1.controller;


import br.com.builders.basecliente.api.v1.dto.CustomerRequestDto;
import br.com.builders.basecliente.api.v1.dto.CustomerResponseDto;
import br.com.builders.basecliente.jpa.entity.Customer;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;


@Api(value = "/v1/customers", produces = "application/json", tags = "Customers", description = "Api para controle de base de clientes")
public interface CustomerController {


    @ApiOperation(value = "Criação de cliente da Builders", response = CustomerResponseDto.class, httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 201, message = "Criado"),
            @ApiResponse(code = 400, message = "Erros de negócio"),
            @ApiResponse(code = 401, message = "Falha em autenticação"),
            @ApiResponse(code = 404, message = "Documento não encontrado"),
            @ApiResponse(code = 500, message = "Erros internos não identificados")
    })
    public ResponseEntity<CustomerResponseDto> create(@ApiParam(value = "Objeto com dados do cliente", required = true) final CustomerRequestDto customer);

    @ApiOperation(value = "Buscar cliente pelo id", response = CustomerResponseDto.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Erros de negócio"),
            @ApiResponse(code = 401, message = "Falha em autenticação"),
            @ApiResponse(code = 404, message = "Identidicador não encontrado"),
            @ApiResponse(code = 500, message = "Erros internos não identificados")
    })
    public CustomerResponseDto findById(@ApiParam(value ="Identificador do cliente", required = false) final Long customerId);

    @ApiOperation(value = "Atualizar dados de cliente pelo id", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Erros de negócio"),
            @ApiResponse(code = 401, message = "Falha em autenticação"),
            @ApiResponse(code = 404, message = "Identidicador não encontrado"),
            @ApiResponse(code = 500, message = "Erros internos não identificados")
    })
    public ResponseEntity<CustomerResponseDto> update(@ApiParam(value = "Identificador do cliente", required = true) Long customerId, @ApiParam(value = "Objeto com dados de Cliente para atualizacao", required = true) final CustomerRequestDto customer);

    @ApiOperation(value = "Apagar cliente pelo id", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Erros de negócio"),
            @ApiResponse(code = 401, message = "Falha em autenticação"),
            @ApiResponse(code = 404, message = "Identidicador não encontrado"),
            @ApiResponse(code = 500, message = "Erros internos não identificados")
    })
    public ResponseEntity<CustomerResponseDto> delete(@ApiParam(value ="Identificador do cliente", required = false) final Long customerId);


    @ApiOperation(value = "Buscar por filtro e paginação", response = CustomerResponseDto.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Erros de negócio"),
            @ApiResponse(code = 401, message = "Falha em autenticação"),
            @ApiResponse(code = 500, message = "Erros internos não identificados")
    })
    Page<CustomerResponseDto> fetchCustomersAsFilteredList(@ApiParam(value = "Nome do cliente", required = false) String nameFilter
            , @ApiParam(value = "CPF", required = false) String cpfFilter
            , @ApiParam(value = "Data de nascimento (yyyy-mm-dd)", required = false) LocalDate birthDateFilter
            , @ApiParam(value = "Número Pagina iniciando em 0") Integer page
            , @ApiParam(value = "Quantidade de registros por página") Integer size);

}
