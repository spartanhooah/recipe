package net.frey.recipe.service;

import net.frey.recipe.command.IngredientCommand;
import net.frey.recipe.domain.Ingredient;

import java.util.Set;

public interface IngredientService {
    Set<Ingredient> getIngredients();

    Ingredient findById(Long id);

    Ingredient findByIdFullyPopulated(Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    Object findCommandById(Long id);

    void deleteById(Long id);
}
