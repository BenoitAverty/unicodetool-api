package org.unicodetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@EnableCaching
public class UnicodetoolApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UnicodetoolApiApplication.class);

		Properties defaultProps = new Properties();
		defaultProps.setProperty("spring.profiles.active", "dev");
		app.setDefaultProperties(defaultProps);

		app.run(args);
	}
}
