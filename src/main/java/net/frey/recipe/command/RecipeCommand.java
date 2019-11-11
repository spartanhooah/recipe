package net.frey.recipe.command;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import net.frey.recipe.domain.Difficulty;

@Data
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
}
