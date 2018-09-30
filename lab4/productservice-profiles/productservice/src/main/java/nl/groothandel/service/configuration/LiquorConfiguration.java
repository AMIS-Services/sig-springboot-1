package nl.groothandel.service.configuration;

import nl.groothandel.service.domain.ProductRepository;
import nl.groothandel.service.domain.ProductRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("Liquor")
public class LiquorConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }
}
