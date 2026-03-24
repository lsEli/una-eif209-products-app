package cr.ac.una.productsapplication.dtos.view;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductView(Long id, String name, double basePrice, double finalPrice, String currency, boolean active,
                          LocalDateTime createdAt, Long categoryId, String categoryName, String manufacturer,
                          String warrantyInfo, String description, Set<Long> tagIds, Set<String> tagNames) {
}
