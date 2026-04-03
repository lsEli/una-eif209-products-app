package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.config.AppProperties;
import cr.ac.una.productsapplication.dtos.form.ProductForm;
import cr.ac.una.productsapplication.dtos.view.ProductView;
import cr.ac.una.productsapplication.exceptions.CategoryNotFoundException;
import cr.ac.una.productsapplication.exceptions.ProductNotFoundException;
import cr.ac.una.productsapplication.exceptions.TagNotFoundException;
import cr.ac.una.productsapplication.models.Category;
import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.models.ProductDetail;
import cr.ac.una.productsapplication.models.Tag;
import cr.ac.una.productsapplication.repositories.ICategoryRepository;
import cr.ac.una.productsapplication.repositories.IProductRepository;
import cr.ac.una.productsapplication.repositories.ITagRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ITagRepository tagRepository;
    private final AppProperties appProperties;

    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository, ITagRepository tagRepository, AppProperties appProperties) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.appProperties = appProperties;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Transactional(readOnly = true)
    public List<ProductView> findAll() {
        return productRepository.findByActiveTrue().stream().map(this::toView).toList();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Transactional(readOnly = true)
    public ProductView findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return toView(product);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Transactional(readOnly = true)
    public List<ProductView> searchByName(String name) {
        return productRepository.findByActiveTrueAndNameContainingIgnoreCase(name.trim()).stream().map(this::toView).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void create(ProductForm form) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(form.getCategoryId()));

        Set<Tag> tags = resolveTags(form.getTagIds());

        Product product = new Product();
        product.setName(form.getName().trim());
        product.setPrice(form.getPrice());
        product.setCategory(category);
        product.setTags(tags);

        if (hasDetailData(form)) {
            ProductDetail detail = new ProductDetail();
            detail.setManufacturer(form.getManufacturer());
            detail.setWarrantyInfo(form.getWarrantyInfo());
            detail.setDescription(form.getDescription());
            product.setDetail(detail);
        }

        productRepository.save(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void update(Long id, ProductForm form) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(form.getCategoryId()));

        Set<Tag> tags = resolveTags(form.getTagIds());

        product.setName(form.getName().trim());
        product.setPrice(form.getPrice());
        product.setCategory(category);
        product.setTags(tags);

        if (hasDetailData(form)) {
            ProductDetail detail = product.getDetail();
            if (detail == null) {
                detail = new ProductDetail();
                product.setDetail(detail);
            }
            detail.setManufacturer(form.getManufacturer());
            detail.setWarrantyInfo(form.getWarrantyInfo());
            detail.setDescription(form.getDescription());
        } else {
            product.setDetail(null);
        }

        productRepository.save(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLogical(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(false);
        productRepository.save(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public ProductForm buildFormForEdit(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        ProductForm form = new ProductForm();
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        form.setCategoryId(product.getCategory().getId());
        form.setTagIds(product.getTags().stream().map(Tag::getId).collect(Collectors.toSet()));

        if (product.getDetail() != null) {
            form.setManufacturer(product.getDetail().getManufacturer());
            form.setWarrantyInfo(product.getDetail().getWarrantyInfo());
            form.setDescription(product.getDetail().getDescription());
        }

        return form;
    }

    private boolean hasDetailData(ProductForm form) {
        return hasText(form.getManufacturer()) || hasText(form.getWarrantyInfo()) || hasText(form.getDescription());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        Set<Tag> tags = new HashSet<>();

        if (tagIds == null || tagIds.isEmpty()) {
            return tags;
        }

        for (Long id : tagIds) {
            Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
            tags.add(tag);
        }

        return tags;
    }

    private ProductView toView(Product product) {
        double finalPrice = product.getPrice() + (product.getPrice() * appProperties.getTaxRate());

        String manufacturer = null;
        String warrantyInfo = null;
        String description = null;

        if (product.getDetail() != null) {
            manufacturer = product.getDetail().getManufacturer();
            warrantyInfo = product.getDetail().getWarrantyInfo();
            description = product.getDetail().getDescription();
        }

        return new ProductView(product.getId(), product.getName(), product.getPrice(), finalPrice, appProperties.getDefaultCurrency(), product.isActive(), product.getCreatedAt(), product.getCategory().getId(), product.getCategory().getName(), manufacturer, warrantyInfo, description, product.getTags().stream().map(Tag::getId).collect(Collectors.toSet()), product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }
}
