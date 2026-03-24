package cr.ac.una.productsapplication.config;

import cr.ac.una.productsapplication.models.Category;
import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.models.ProductDetail;
import cr.ac.una.productsapplication.models.Tag;
import cr.ac.una.productsapplication.repositories.ICategoryRepository;
import cr.ac.una.productsapplication.repositories.IProductRepository;
import cr.ac.una.productsapplication.repositories.ITagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ICategoryRepository categoryRepository, ITagRepository tagRepository, IProductRepository productRepository) {
        return args -> {
            if (categoryRepository.count() > 0 || productRepository.count() > 0 || tagRepository.count() > 0) {
                return;
            }

            Category laptops = categoryRepository.save(new Category("Laptops"));
            Category accesorios = categoryRepository.save(new Category("Accesorios"));

            Tag gaming = tagRepository.save(new Tag("Gaming"));
            Tag oficina = tagRepository.save(new Tag("Oficina"));
            Tag inalambrico = tagRepository.save(new Tag("Inalámbrico"));

            Product p1 = new Product();
            p1.setName("Laptop Lenovo ThinkPad");
            p1.setPrice(450000);
            p1.setCategory(laptops);
            p1.setTags(new HashSet<>());
            p1.getTags().add(oficina);

            ProductDetail detail1 = new ProductDetail();
            detail1.setManufacturer("Lenovo");
            detail1.setWarrantyInfo("12 meses");
            detail1.setDescription("Laptop empresarial para trabajo y estudio.");
            p1.setDetail(detail1);

            Product p2 = new Product();
            p2.setName("Mouse Logitech MX");
            p2.setPrice(32000);
            p2.setCategory(accesorios);
            p2.setTags(new HashSet<>());
            p2.getTags().add(oficina);
            p2.getTags().add(inalambrico);

            Product p3 = new Product();
            p3.setName("Teclado Mecánico RGB");
            p3.setPrice(48000);
            p3.setCategory(accesorios);
            p3.setTags(new HashSet<>());
            p3.getTags().add(gaming);

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
        };
    }
}
