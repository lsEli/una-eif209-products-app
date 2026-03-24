package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.form.TagForm;
import cr.ac.una.productsapplication.services.TagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagViewController {

    private final TagService service;

    public TagViewController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tags", service.findAll());
        model.addAttribute("pageTitle", "Etiquetas");
        return "tags/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("tagForm", new TagForm());
        model.addAttribute("pageTitle", "Nueva etiqueta");
        return "tags/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("tagForm") TagForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Nueva etiqueta");
            return "tags/form";
        }

        service.create(form);
        return "redirect:/tags";
    }
}
