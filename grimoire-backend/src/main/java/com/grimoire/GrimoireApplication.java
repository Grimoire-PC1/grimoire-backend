package com.grimoire;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GrimoireApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrimoireApplication.class, args);
		log.info("Grimoire Application started successfully!");
	}

}
