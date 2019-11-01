package net.frey.recipe.controller;

import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {
    private static final Long DEFAULT_ID = 1L;
    private MockMvc mockMvc;
    private RecipeCommand recipeCommand;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Before
    public void setUp() {
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        recipeCommand = new RecipeCommand();
        recipeCommand.setId(DEFAULT_ID);
        when(recipeService.saveRecipeCommand(recipeCommand)).thenReturn(recipeCommand);
    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void postNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/2"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/recipe/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(1L);
    }
}
