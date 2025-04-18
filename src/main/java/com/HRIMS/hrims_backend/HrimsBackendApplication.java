package com.HRIMS.hrims_backend;

import com.HRIMS.hrims_backend.config.AttendanceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.HRIMS.hrims_backend")
@EnableConfigurationProperties(AttendanceProperties.class)
public class HrimsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrimsBackendApplication.class, args);
	}

}
