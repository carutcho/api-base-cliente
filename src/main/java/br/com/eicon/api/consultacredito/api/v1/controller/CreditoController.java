package br.com.eicon.api.consultacredito.api.v1.controller;

import br.com.eicon.api.consultacredito.api.v1.dto.CreditoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Tag(name = "Creditos", description = "API para consulta de creditos constituidos")
public interface CreditoController {

    @Operation(summary = "Listar todas as NFS-es")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    ResponseEntity<Collection<CreditoResponseDto>> listarTodasNfe();

    @Operation(summary = "Listar creditos por NFS-e")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    ResponseEntity<Collection<CreditoResponseDto>> buscarPorNfse(
            @Parameter(description = "Numero identificador da NFS-e", required = true)
            @PathVariable String numeroNfse);

    @Operation(summary = "Buscar credito por numero")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "404", description = "Credito nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    ResponseEntity<CreditoResponseDto> buscarPorNumero(
            @Parameter(description = "Numero do credito constituido", required = true)
            @PathVariable String numeroCredito);
}
