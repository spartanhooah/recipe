package net.frey.recipe.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.IngredientCommand;
import net.frey.recipe.converters.IngredientCommandToIngredient;
import net.frey.recipe.converters.IngredientToIngredientCommand;
import net.frey.recipe.domain.Ingredient;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.IngredientRepository;
import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceJpa implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // TODO: implement error handling
            throw new RuntimeException("Recipe ID " + recipeId + " was not found.");
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientCommandOptional =
                    recipe.getIngredients().stream()
                            .filter(ingredient -> ingredient.getId().equals(ingredientId))
                            .map(ingredientToIngredientCommand::convert)
                            .findFirst();

            if (ingredientCommandOptional.isEmpty()) {
                // TODO: implement error handling
                log.error("Ingredient ID {} was not found.", ingredientId);
            }

            IngredientCommand ingredientCommand = ingredientCommandOptional.get();
            ingredientCommand.setRecipeId(recipeId);

            return ingredientCommand;
        }
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional =
                recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isEmpty()) {
            // TODO: implement error handling
            throw new RuntimeException(
                    "Recipe not found for ID " + ingredientCommand.getRecipeId());
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional =
                    recipe.getIngredients().stream()
                            .filter(
                                    ingredient ->
                                            ingredient.getId().equals(ingredientCommand.getId()))
                            .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient foundIngredient = ingredientOptional.get();
                foundIngredient.setDescription(ingredientCommand.getDescription());
                foundIngredient.setAmount(ingredientCommand.getAmount());

                foundIngredient.setUnitOfMeasure(
                        unitOfMeasureRepository
                                .findById(ingredientCommand.getUnitOfMeasure().getId())
                                .orElseThrow(
                                        () -> // TODO: improve this
                                        new RuntimeException(
                                                        "Could not find provided UnitOfMeasure with ID "
                                                                + ingredientCommand
                                                                        .getUnitOfMeasure()
                                                                        .getId())));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional =
                    savedRecipe.getIngredients().stream()
                            .filter(
                                    recipeIngredient ->
                                            recipeIngredient
                                                    .getId()
                                                    .equals(ingredientCommand.getId()))
                            .findFirst();

            if (savedIngredientOptional.isEmpty()) {
                savedIngredientOptional =
                        savedRecipe.getIngredients().stream()
                                .filter(
                                        recipeIngredient ->
                                                recipeIngredient
                                                        .getDescription()
                                                        .equals(ingredientCommand.getDescription()))
                                .filter(
                                        recipeIngredient ->
                                                recipeIngredient
                                                        .getAmount()
                                                        .equals(ingredientCommand.getAmount()))
                                .filter(
                                        recipeIngredient ->
                                                recipeIngredient
                                                        .getUnitOfMeasure()
                                                        .getId()
                                                        .equals(
                                                                ingredientCommand
                                                                        .getUnitOfMeasure()
                                                                        .getId()))
                                .findFirst();
            }

            // TODO: check for fail

            IngredientCommand ingredientCommandSaved =
                    ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return ingredientCommandSaved;
        }
    }

    @Override
    public void deleteById(String ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
