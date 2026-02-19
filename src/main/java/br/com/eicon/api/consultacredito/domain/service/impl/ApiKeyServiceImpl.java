package br.com.eicon.api.consultacredito.domain.service.impl;

import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateRequestDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyCreateResponseDto;
import br.com.eicon.api.consultacredito.api.v1.dto.ApiKeyListResponseDto;
import br.com.eicon.api.consultacredito.config.security.ApiKeyHashUtil;
import br.com.eicon.api.consultacredito.domain.exception.BusinessException;
import br.com.eicon.api.consultacredito.domain.exception.CodigoMensagem;
import br.com.eicon.api.consultacredito.domain.service.ApiKeyService;
import br.com.eicon.api.consultacredito.jpa.entity.ApiKey;
import br.com.eicon.api.consultacredito.jpa.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private static final String KEY_PREFIX = "eicon_";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final ApiKeyRepository apiKeyRepository;

    @Override
    @Transactional
    public ApiKeyCreateResponseDto criar(ApiKeyCreateRequestDto request) {
        String rawKey = gerarChaveRaw();

        ApiKey apiKey = ApiKey.builder()
                .name(request.getName())
                .keyValue(ApiKeyHashUtil.hash(rawKey))
                .active(true)
                .build();

        ApiKey salva = apiKeyRepository.save(apiKey);

        return ApiKeyCreateResponseDto.builder()
                .id(salva.getId())
                .name(salva.getName())
                .keyValue(rawKey)
                .createdAt(salva.getCreatedAt())
                .aviso("Guarde esta chave agora. Ela nao podera ser exibida novamente.")
                .build();
    }

    @Override
    public List<ApiKeyListResponseDto> listar() {
        return apiKeyRepository.findAll().stream()
                .map(key -> ApiKeyListResponseDto.builder()
                        .id(key.getId())
                        .name(key.getName())
                        .active(key.isActive())
                        .createdAt(key.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void revogar(Long id) {
        ApiKey apiKey = apiKeyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CodigoMensagem.RN_API_KEY_NAO_ENCONTRADA));

        if (!apiKey.isActive()) {
            throw new BusinessException(CodigoMensagem.RN_API_KEY_JA_REVOGADA);
        }

        apiKey.setActive(false);
        apiKeyRepository.save(apiKey);
    }

    private String gerarChaveRaw() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return KEY_PREFIX + HexFormat.of().formatHex(bytes);
    }
}
