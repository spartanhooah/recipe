package net.frey.recipe.converters;

import java.util.stream.Collectors;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoriesConverter;
    private final NotesToNotesCommand notesCoverter;
    private final IngredientToIngredientCommand ingredientsConverter;

    public RecipeToRecipeCommand(
            CategoryToCategoryCommand categoriesConverter,
            NotesToNotesCommand notesCoverter,
            IngredientToIngredientCommand ingredientsConverter) {
        this.categoriesConverter = categoriesConverter;
        this.notesCoverter = notesCoverter;
        this.ingredientsConverter = ingredientsConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setNotes(notesCoverter.convert(source.getNotes()));
        recipeCommand.setIngredients(
                source.getIngredients().stream()
                        .map(ingredientsConverter::convert)
                        .collect(Collectors.toSet()));
        recipeCommand.setCategories(
                source.getCategories().stream()
                        .map(categoriesConverter::convert)
                        .collect(Collectors.toSet()));
        recipeCommand.setImage(source.getImage());

        log.debug("successfully converted recipe to recipe command");

        return recipeCommand;
    }
}
