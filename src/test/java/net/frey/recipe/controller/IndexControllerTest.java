package net.frey.recipe.controller;

import net.frey.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        String result = indexController.getIndexPage(model);

        assertThat(result, is("index"));

        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        verify(recipeService, times(1)).getRecipes();
    }
}