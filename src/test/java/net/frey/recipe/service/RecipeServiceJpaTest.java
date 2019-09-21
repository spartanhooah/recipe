package net.frey.recipe.service;

import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceJpaTest {
    private RecipeServiceJpa recipeServiceJpa;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        recipeServiceJpa = new RecipeServiceJpa(recipeRepository);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeServiceJpa.getRecipes()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeServiceJpa.getRecipes();

        assertThat(recipes.size(), is(1));

        verify(recipeRepository, times(1)).findAll();
    }
}