package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    List<Product> findByActiveTrueAndNameContainingIgnoreCase(String name);
}
