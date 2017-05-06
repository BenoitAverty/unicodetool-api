package org.unicodetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UnicodetoolServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnicodetoolServerApplication.class, args);
	}
}
