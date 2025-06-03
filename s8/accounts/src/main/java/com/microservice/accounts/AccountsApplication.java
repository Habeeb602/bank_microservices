package com.microservice.accounts;

import com.microservice.accounts.dto.AccountsContactDetailsDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactDetailsDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "YesBank Accounts Microservices Documentation",
				contact = @Contact(
						name = "Habeeb Rahman",
						email = "habeeb@gmail.com",
						url = "https://yesbank.com"
						),
				description = "YesBank Accounts handling APIs",
				license = @License(
						name = "Apache licensing",
						url = "apache.com"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
