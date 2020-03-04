package net.frey.recipe.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.converters.RecipeCommandToRecipe;
import net.frey.recipe.converters.RecipeToRecipeCommand;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.exception.NotFoundException;
import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.repository.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<Recipe> findById(String id) {
        return recipeRepository.findById(id);

//        log.debug("found recipe with id " + id);
//        // To non-lazily load (I think)
//        recipeOptional.get().getCategories().size();
//        recipeOptional.get().getIngredients().size();
    }

    @Override
    public Mono<Recipe> findByIdFullyPopulated(String id) {
        return this.findById(id);
//
//        // To non-lazily load (I think)
//        recipe.getCategories().size();
//        recipe.getIngredients().size();
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe).block();

        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {
        return Mono.just(recipeToRecipeCommand.convert(findById(id).block()));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return recipeRepository.deleteById(id);
    }
}
