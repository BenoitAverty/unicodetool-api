package org.unicodetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
public class UnicodetoolApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UnicodetoolApiApplication.class);
		Map<String, Object> defaultProps = new HashMap<>();
		defaultProps.put("spring.profiles.active", "dev");
		app.setDefaultProperties(defaultProps);

		app.run(args);
	}
}
