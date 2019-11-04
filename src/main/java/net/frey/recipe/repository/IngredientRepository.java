package net.frey.recipe.repository;

import net.frey.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Optional<Ingredient> findIngredientByDescription(String description);
}
