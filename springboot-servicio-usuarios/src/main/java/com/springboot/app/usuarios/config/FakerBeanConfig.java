package com.springboot.app.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class FakerBeanConfig {
	
	@Bean
	public Faker getFaker() {
		return new Faker();
	}
}
