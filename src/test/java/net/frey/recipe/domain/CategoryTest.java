package net.frey.recipe.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    private static final Long ID = 4L;
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
    public void getRecipes() {
    }

    @Test
    public void getDescription() {
    }
}
