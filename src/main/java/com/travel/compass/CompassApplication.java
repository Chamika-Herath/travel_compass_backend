//package com.travel.compass;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class CompassApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CompassApplication.class, args);
//	}
//
//}


package com.travel.compass;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CompassApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompassApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper(); // Basic configuration
	}
}
