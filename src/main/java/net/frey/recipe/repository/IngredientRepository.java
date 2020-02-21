package net.frey.recipe.repository;

import java.util.Optional;
import net.frey.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    Optional<Ingredient> findIngredientByDescription(String description);
}
