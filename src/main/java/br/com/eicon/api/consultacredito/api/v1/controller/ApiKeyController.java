package br.com.eicon.api.consultacredito.api.v1.controller;

import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateRequestDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateResponseDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "API Keys", description = "Gerenciamento de API Keys para integracoes machine-to-machine")
@SecurityRequirement(name = "bearerAuth")
public interface ApiKeyController {

    @Operation(
            summary = "Criar nova API Key",
            description = "Gera uma nova API Key. O valor da chave e exibido uma unica vez e nao pode ser recuperado posteriormente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "API Key criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisicao invalida"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<ApiKeyCreateResponseDto> criar(@RequestBody @Valid ApiKeyCreateRequestDto request);

    @Operation(summary = "Listar API Keys", description = "Retorna todas as API Keys cadastradas sem exibir o valor da chave.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<List<ApiKeyListResponseDto>> listar();

    @Operation(summary = "Revogar API Key", description = "Desativa uma API Key. A operacao e irreversivel via API.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "API Key revogada com sucesso"),
            @ApiResponse(responseCode = "400", description = "API Key ja revogada"),
            @ApiResponse(responseCode = "401", description = "Nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "API Key nao encontrada")
    })
    ResponseEntity<Void> revogar(
            @Parameter(description = "ID da API Key", required = true)
            @PathVariable Long id);
}
