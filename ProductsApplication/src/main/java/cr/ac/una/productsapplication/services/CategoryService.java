package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.dtos.form.CategoryForm;
import cr.ac.una.productsapplication.dtos.view.CategoryOptionView;
import cr.ac.una.productsapplication.models.Category;
import cr.ac.una.productsapplication.repositories.ICategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<CategoryOptionView> findAll() {
        return repository.findAll().stream().map(c -> new CategoryOptionView(c.getId(), c.getName())).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void create(CategoryForm form) {
        Category category = new Category(form.getName().trim());
        repository.save(category);
    }
}
