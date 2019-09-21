package net.frey.recipe.domain;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        category.setId(4L);
        assertEquals(4:category.getId());
    }

    @Test
    public void getRecipes() {
    }

    @Test
    public void getDescription() {
    }
}