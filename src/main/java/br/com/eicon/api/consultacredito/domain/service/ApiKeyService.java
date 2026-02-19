package br.com.eicon.api.consultacredito.domain.service;

import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateRequestDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateResponseDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyListResponseDto;

import java.util.List;

public interface ApiKeyService {

    ApiKeyCreateResponseDto criar(ApiKeyCreateRequestDto request);

    List<ApiKeyListResponseDto> listar();

    void revogar(Long id);
}
