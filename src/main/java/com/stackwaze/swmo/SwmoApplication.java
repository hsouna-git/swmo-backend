package com.stackwaze.swmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwmoApplication.class, args);
	}

}
