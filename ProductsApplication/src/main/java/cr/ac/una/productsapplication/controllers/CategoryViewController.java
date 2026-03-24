package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.form.CategoryForm;
import cr.ac.una.productsapplication.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryViewController {

    private final CategoryService service;

    public CategoryViewController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", service.findAll());
        model.addAttribute("pageTitle", "Categorías");
        return "categories/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("pageTitle", "Nueva categoría");
        return "categories/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("categoryForm") CategoryForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Nueva categoría");
            return "categories/form";
        }

        service.create(form);
        return "redirect:/categories";
    }
}
