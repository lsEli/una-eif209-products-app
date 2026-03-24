package cr.ac.una.productsapplication;

import cr.ac.una.productsapplication.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProductsApplication {

    static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }
}
