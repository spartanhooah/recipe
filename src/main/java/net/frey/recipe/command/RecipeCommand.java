package net.frey.recipe.command;

import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import net.frey.recipe.domain.Difficulty;
import org.hibernate.validator.constraints.URL;

@Data
public class RecipeCommand {
    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    @URL @NotBlank private String url;

    @NotNull private String directions;

    private String source;
    private List<IngredientCommand> ingredients = new LinkedList<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private List<CategoryCommand> categories = new LinkedList<>();
    private Byte[] image;
}
