package cr.ac.una.productsapplication.dtos.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class ProductForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Positive(message = "El precio debe ser mayor que cero")
    private double price;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    private Set<Long> tagIds = new HashSet<>();

    private String manufacturer;
    private String warrantyInfo;
    private String description;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getWarrantyInfo() {
        return warrantyInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setWarrantyInfo(String warrantyInfo) {
        this.warrantyInfo = warrantyInfo;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
