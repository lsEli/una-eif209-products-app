package cr.ac.una.productsapplication.exceptions;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id) {
        super("Etiqueta no encontrada con id: " + id);
    }
}
