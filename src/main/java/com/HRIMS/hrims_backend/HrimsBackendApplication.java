package com.HRIMS.hrims_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.HRIMS.hrims_backend")
public class HrimsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrimsBackendApplication.class, args);
	}

}
