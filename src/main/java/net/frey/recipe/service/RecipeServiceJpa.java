package net.frey.recipe.service;

import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.converters.RecipeCommandToRecipe;
import net.frey.recipe.converters.RecipeToRecipeCommand;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceJpa implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceJpa(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    @Transactional
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Could not find recipe with id " + id);
        }

        recipeOptional.get().getCategories().size();
        recipeOptional.get().getIngredients().size();

        return recipeOptional.get();
    }

    @Override
    public Recipe findByIdFullyPopulated(Long id) {
        Recipe recipe = this.findById(id);

        recipe.getCategories().size();
        recipe.getIngredients().size();

        return recipe;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
