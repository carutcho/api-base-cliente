package br.com.builders.basecliente.api.v1.controller;

import br.com.builders.basecliente.api.v1.dto.CustomerResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Endpoint para verificar se servico esta no ar
@RestController
@RequestMapping(path = "/health")
public class HealthCheckController {

    @ApiOperation(value = "Endpoint para verificar se o serviço está online. Sempre retornará Status OK e True para sinalizar que está online!", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
    })
    @GetMapping(path = "/check")
    public ResponseEntity<Boolean> check() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
