package br.com.eicon.api.consultacredito;

import br.com.eicon.api.consultacredito.config.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
public class ApiConsultaCreditoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiConsultaCreditoApplication.class, args);
    }
}
