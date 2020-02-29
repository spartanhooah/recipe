package net.frey.recipe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class Ingredient {
    private String id = UUID.randomUUID().toString();;
    private String description;
    private BigDecimal amount;

    @DBRef private UnitOfMeasure unitOfMeasure;

    public Ingredient(
            String description, BigDecimal amount, Recipe recipe, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient() {}
}
