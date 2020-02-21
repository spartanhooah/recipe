package net.frey.recipe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
    private static final String ID = "4";
    private Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        category.setId(ID);

        assertEquals(ID, category.getId());
    }

    @Test
    public void getRecipes() {}

    @Test
    public void getDescription() {}
}
