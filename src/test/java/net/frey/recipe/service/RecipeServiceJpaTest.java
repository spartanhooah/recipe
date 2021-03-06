package net.frey.recipe.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.frey.recipe.converters.RecipeCommandToRecipe;
import net.frey.recipe.converters.RecipeToRecipeCommand;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.exception.NotFoundException;
import net.frey.recipe.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceJpaTest {
    @InjectMocks private RecipeServiceJpa recipeServiceJpa;

    @Mock private RecipeRepository recipeRepository;

    @Mock private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock private RecipeToRecipeCommand recipeToRecipeCommand;

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

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeServiceJpa.findById(1L);

        assertThat(returnedRecipe, is(notNullValue()));
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void deleteRecipeById() {
        Long idToDelete = 2L;
        recipeServiceJpa.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(idToDelete);
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeIdNotFound() {
        Optional<Recipe> recipe = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipe);

        recipeServiceJpa.findById(1L);
    }
}
