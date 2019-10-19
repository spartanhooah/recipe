package net.frey.recipe.converters;

import lombok.Synchronized;
import net.frey.recipe.command.IngredientCommand;
import net.frey.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure unitConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setUnitOfMeasure(unitConverter.convert(source.getUnitOfMeasure()));
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());

        return ingredient;
    }
}
