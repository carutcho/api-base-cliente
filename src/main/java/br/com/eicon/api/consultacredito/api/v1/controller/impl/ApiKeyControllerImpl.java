package br.com.eicon.api.consultacredito.api.v1.controller.impl;

import br.com.eicon.api.consultacredito.api.v1.controller.ApiKeyController;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateRequestDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateResponseDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyListResponseDto;
import br.com.eicon.api.consultacredito.domain.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api-keys")
@RequiredArgsConstructor
public class ApiKeyControllerImpl implements ApiKeyController {

    private final ApiKeyService apiKeyService;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiKeyCreateResponseDto> criar(@RequestBody ApiKeyCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.criar(request));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApiKeyListResponseDto>> listar() {
        return ResponseEntity.ok(apiKeyService.listar());
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> revogar(@PathVariable Long id) {
        apiKeyService.revogar(id);
        return ResponseEntity.noContent().build();
    }
}
