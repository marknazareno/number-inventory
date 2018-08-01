package com.mnazareno.numberinventory;

import com.mnazareno.numberinventory.service.PhoneNumberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NumberInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberInventoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(PhoneNumberService phoneNumberService) {
		return args -> phoneNumberService.addNumbers(1000);
	}
}
