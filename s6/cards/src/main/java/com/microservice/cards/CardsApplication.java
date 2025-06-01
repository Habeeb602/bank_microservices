package com.microservice.cards;

import com.microservice.cards.dto.CardsContactDetailsInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = {CardsContactDetailsInfo.class})
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
