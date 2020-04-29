package br.com.fiap.trips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = TripsApplication.class)
@EnableAutoConfiguration
public class TripsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripsApplication.class, args);
	}

}
