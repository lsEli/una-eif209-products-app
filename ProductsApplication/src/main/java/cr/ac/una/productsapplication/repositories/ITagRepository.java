package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepository extends JpaRepository<Tag, Long> {
}
