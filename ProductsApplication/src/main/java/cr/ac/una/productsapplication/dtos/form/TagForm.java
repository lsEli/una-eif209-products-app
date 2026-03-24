package cr.ac.una.productsapplication.dtos.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
