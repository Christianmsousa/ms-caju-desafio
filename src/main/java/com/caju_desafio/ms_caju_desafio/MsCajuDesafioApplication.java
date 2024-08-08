package com.caju_desafio.ms_caju_desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class MsCajuDesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCajuDesafioApplication.class, args);
	}

}
