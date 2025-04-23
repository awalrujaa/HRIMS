package com.HRIMS.hrims_backend;

import com.HRIMS.hrims_backend.config.AttendanceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.HRIMS.hrims_backend")
@EnableConfigurationProperties(AttendanceProperties.class)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class HrimsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrimsBackendApplication.class, args);
	}

}
