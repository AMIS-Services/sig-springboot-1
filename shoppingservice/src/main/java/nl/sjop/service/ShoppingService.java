package nl.sjop.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"nl.sjop"})
public class ShoppingService {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingService.class, args);
    }
}
