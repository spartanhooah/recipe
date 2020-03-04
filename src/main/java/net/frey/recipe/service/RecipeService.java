package net.frey.recipe.service;

import java.util.Set;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<Recipe> findByIdFullyPopulated(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<Void> deleteById(String id);
}
