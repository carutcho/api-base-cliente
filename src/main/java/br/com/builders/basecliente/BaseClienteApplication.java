package br.com.builders.basecliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class BaseClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseClienteApplication.class, args);
	}

}