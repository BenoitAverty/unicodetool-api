package org.unicodetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@EnableCaching
public class UnicodetoolServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UnicodetoolServerApplication.class);
		Map<String, Object> defaultProps = new HashMap<>();
		defaultProps.put("spring.profiles.active", "dev");
		app.setDefaultProperties(defaultProps);

		app.run(args);
	}
}
