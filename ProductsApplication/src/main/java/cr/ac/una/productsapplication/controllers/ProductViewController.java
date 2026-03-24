package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.form.ProductForm;
import cr.ac.una.productsapplication.services.CategoryService;
import cr.ac.una.productsapplication.services.ProductService;
import cr.ac.una.productsapplication.services.TagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final TagService tagService;

    public ProductViewController(ProductService productService, CategoryService categoryService, TagService tagService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.trim().isEmpty()) {
            model.addAttribute("products", productService.searchByName(name));
            model.addAttribute("searchTerm", name);
        } else {
            model.addAttribute("products", productService.findAll());
            model.addAttribute("searchTerm", "");
        }

        model.addAttribute("pageTitle", "Productos");
        return "products/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("pageTitle", "Detalle de producto");
        return "products/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        loadCommonFormData(model);
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("pageTitle", "Nuevo producto");
        model.addAttribute("formTitle", "Nuevo producto");
        model.addAttribute("formAction", "/products/save");
        model.addAttribute("isEdit", false);
        return "products/form";
    }

    @PostMapping("/save")
    public String create(@Valid @ModelAttribute("productForm") ProductForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadCommonFormData(model);
            model.addAttribute("pageTitle", "Nuevo producto");
            model.addAttribute("formTitle", "Nuevo producto");
            model.addAttribute("formAction", "/products/save");
            model.addAttribute("isEdit", false);
            return "products/form";
        }

        productService.create(form);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        loadCommonFormData(model);
        model.addAttribute("productForm", productService.buildFormForEdit(id));
        model.addAttribute("productId", id);
        model.addAttribute("pageTitle", "Editar producto");
        model.addAttribute("formTitle", "Editar producto");
        model.addAttribute("formAction", "/products/" + id + "/update");
        model.addAttribute("isEdit", true);
        return "products/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("productForm") ProductForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadCommonFormData(model);
            model.addAttribute("productId", id);
            model.addAttribute("pageTitle", "Editar producto");
            model.addAttribute("formTitle", "Editar producto");
            model.addAttribute("formAction", "/products/" + id + "/update");
            model.addAttribute("isEdit", true);
            return "products/form";
        }

        productService.update(id, form);
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteLogical(id);
        return "redirect:/products";
    }

    private void loadCommonFormData(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("tags", tagService.findAll());
    }
}
