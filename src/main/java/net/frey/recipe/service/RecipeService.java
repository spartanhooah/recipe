package net.frey.recipe.service;

import java.util.Set;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    Recipe findByIdFullyPopulated(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String id);

    void deleteById(String id);
}
