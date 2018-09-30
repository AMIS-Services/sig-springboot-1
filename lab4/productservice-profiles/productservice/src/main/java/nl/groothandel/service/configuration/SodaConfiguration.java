package nl.groothandel.service.configuration;

import nl.groothandel.service.domain.ProductRepository;
import nl.groothandel.service.domain.SodaProductRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("Soda")
public class SodaConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new SodaProductRepositoryImpl();
    }

}
