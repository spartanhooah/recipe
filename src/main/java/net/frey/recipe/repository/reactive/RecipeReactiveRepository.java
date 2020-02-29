package net.frey.recipe.repository.reactive;

import net.frey.recipe.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
    Mono<Recipe> findByDescription(String description);
}
