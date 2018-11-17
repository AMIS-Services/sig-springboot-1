package nl.groothandel.service;

import nl.groothandel.service.factory.DataServiceFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"nl.groothandel.service"})
public class ProductserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductserviceApplication.class, args);
    }

    @Bean
    ServiceLocatorFactoryBean serviceLocatorFactoryBeanForDataServiceFactory () {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(DataServiceFactory.class);
        return serviceLocatorFactoryBean;
    }
}
