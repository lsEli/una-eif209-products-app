package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
