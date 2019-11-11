package net.frey.recipe.converters;

import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Synchronized;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryConverter;
    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(
            CategoryCommandToCategory categoryConverter,
            NotesCommandToNotes notesConverter,
            IngredientCommandToIngredient ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setDescription(source.getDescription());
        recipe.setCategories(
                source.getCategories().stream()
                        .map(categoryConverter::convert)
                        .collect(Collectors.toSet()));
        recipe.setNotes(Objects.requireNonNull(notesConverter.convert(source.getNotes())));
        recipe.setIngredients(
                source.getIngredients().stream()
                        .map(ingredientConverter::convert)
                        .collect(Collectors.toSet()));

        return recipe;
    }
}
