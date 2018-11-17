package nl.sjop.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"nl.sjop.service"})
public class ShoppingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingserviceApplication.class, args);
	}


}
