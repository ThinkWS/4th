package com.swproject24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.swproject24.repository", "com.swproject24.account"})
public class Swproject24Application {

	public static void main(String[] args) {
		SpringApplication.run(Swproject24Application.class, args);
	}
}