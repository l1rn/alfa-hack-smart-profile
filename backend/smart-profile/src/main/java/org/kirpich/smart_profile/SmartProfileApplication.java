package org.kirpich.smart_profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SmartProfileApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartProfileApplication.class, args);
	}

}
