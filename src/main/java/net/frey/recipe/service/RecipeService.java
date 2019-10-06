package net.frey.recipe.service;

import net.frey.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    Recipe findByIdFullyPopulated(Long id);
}
