package com.kfriday.kevin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KevinApplication {

	public static void main(String[] args) {
		SpringApplication.run(KevinApplication.class, args);
	}

}
