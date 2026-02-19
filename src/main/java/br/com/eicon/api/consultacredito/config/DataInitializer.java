package br.com.eicon.api.consultacredito.config;

import br.com.eicon.api.consultacredito.config.security.ApiKeyHashUtil;
import br.com.eicon.api.consultacredito.jpa.entity.ApiKey;
import br.com.eicon.api.consultacredito.jpa.entity.AppUser;
import br.com.eicon.api.consultacredito.jpa.repository.ApiKeyRepository;
import br.com.eicon.api.consultacredito.jpa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final AppUserRepository userRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.init.admin.username}")
    private String adminUsername;

    @Value("${app.init.admin.password}")
    private String adminPassword;

    @Value("${app.init.api-key.name}")
    private String apiKeyName;

    @Value("${app.init.api-key.value}")
    private String apiKeyValue;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsernameAndActiveTrue(adminUsername).isEmpty()) {
            AppUser admin = AppUser.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .role("ROLE_ADMIN")
                    .active(true)
                    .build();
            userRepository.save(admin);
            log.info("Usuario admin criado: {}", adminUsername);
        }

        if (apiKeyRepository.findByKeyValueAndActiveTrue(ApiKeyHashUtil.hash(apiKeyValue)).isEmpty()) {
            ApiKey key = ApiKey.builder()
                    .keyValue(ApiKeyHashUtil.hash(apiKeyValue))
                    .name(apiKeyName)
                    .active(true)
                    .build();
            apiKeyRepository.save(key);
            log.info("API Key criada: {} | valor original (guarde agora): {}", apiKeyName, apiKeyValue);
        }
    }
}
