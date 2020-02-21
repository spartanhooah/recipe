package net.frey.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
public class Notes {
    @Id
    private String id;
    private String recipeNotes;
}
