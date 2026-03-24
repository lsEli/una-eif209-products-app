package cr.ac.una.productsapplication.exceptions;

import cr.ac.una.productsapplication.controllers.ProductViewController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ProductViewController.class})
public class ViewExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("pageTitle", "Producto no encontrado");
        model.addAttribute("errorTitle", "Producto no encontrado");
        model.addAttribute("errorMessage", ex.getMessage());
        return "products/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("pageTitle", "Error");
        model.addAttribute("errorTitle", "Error inesperado");
        model.addAttribute("errorMessage", "Ocurrió un error inesperado en la aplicación.");
        return "products/error";
    }
}
