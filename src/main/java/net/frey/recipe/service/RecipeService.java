package net.frey.recipe.service;

import java.util.Set;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    Recipe findByIdFullyPopulated(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
