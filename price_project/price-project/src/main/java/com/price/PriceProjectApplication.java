package com.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class PriceProjectApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();
		SpringApplication.run(PriceProjectApplication.class, args);
	}

}
