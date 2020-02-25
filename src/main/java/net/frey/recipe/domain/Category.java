package net.frey.recipe.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Document
public class Category {
    @Id
    private String id;

    @DBRef
    private List<Recipe> recipes = new LinkedList<>();

    private String description;
}
