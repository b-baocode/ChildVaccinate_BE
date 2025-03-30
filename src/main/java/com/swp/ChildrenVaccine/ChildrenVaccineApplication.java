package com.swp.ChildrenVaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ChildrenVaccineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChildrenVaccineApplication.class, args);
		System.out.println("Hello World");
	}

}
