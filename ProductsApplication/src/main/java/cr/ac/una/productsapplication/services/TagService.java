package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.dtos.form.TagForm;
import cr.ac.una.productsapplication.dtos.view.TagOptionView;
import cr.ac.una.productsapplication.models.Tag;
import cr.ac.una.productsapplication.repositories.ITagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    private final ITagRepository repository;

    public TagService(ITagRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TagOptionView> findAll() {
        return repository.findAll().stream().map(t -> new TagOptionView(t.getId(), t.getName())).toList();
    }

    public void create(TagForm form) {
        Tag tag = new Tag(form.getName().trim());
        repository.save(tag);
    }
}
