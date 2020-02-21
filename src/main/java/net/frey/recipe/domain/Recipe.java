package net.frey.recipe.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Recipe {
    @Id
    private String id;
    private String title;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Notes notes;

    @DBRef
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        return title;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
//        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
//        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);

        return this;
    }
}
