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
import net.frey.recipe.repository.reactive.RecipeReactiveRepository;
import net.frey.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Override
    @Transactional
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(
            String recipeId, String ingredientId) {
        return recipeRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(
                        ingredient -> {
                            IngredientCommand command =
                                    ingredientToIngredientCommand.convert(ingredient);
                            command.setRecipeId(recipeId);

                            return command;
                        });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe = recipeRepository.findById(ingredientCommand.getRecipeId()).block();

        if (recipe == null) {
            // TODO: implement error handling
            throw new RuntimeException(
                    "Recipe not found for ID " + ingredientCommand.getRecipeId());
        } else {
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
                                .block());
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe).block();

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

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String ingredientId) {
        ingredientRepository.deleteById(ingredientId);

        return Mono.empty();
    }
}
