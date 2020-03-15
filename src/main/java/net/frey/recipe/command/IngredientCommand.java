package net.frey.recipe.command;

import java.math.BigDecimal;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IngredientCommand {
    private String id;
    private String recipeId;

    @NotBlank
    private String description;

    @Min(1)
    @Max(50)
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand unitOfMeasure;
}
