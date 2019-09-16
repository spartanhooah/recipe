package net.frey.recipe.service;

import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceJpa implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceJpa(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }
}
