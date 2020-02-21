package net.frey.recipe.repository;

import java.util.Optional;
import net.frey.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
    Optional<Recipe> findByDescription(String description);
}
