package net.frey.recipe.controller;

import static org.mockito.Mockito.when;

import net.frey.recipe.domain.Recipe;
import net.frey.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
    private IndexController indexController;

    @Mock private RecipeService recipeService;

    @Mock private Model model;

    @Before
    public void setUp() {
        indexController = new IndexController(recipeService);

        Flux<Recipe> recipes = Flux.just(new Recipe(), new Recipe());
        when(recipeService.getRecipes()).thenReturn(recipes);
    }

    @Test
    public void testMockMVC() throws Exception {
        //        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        //
        // mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        //        ArgumentCaptor<Flux<Recipe>> recipeCaptor = ArgumentCaptor.forClass(Flux.class);
        //
        //        String result = indexController.getIndexPage(model);
        //
        //        assertThat(result, is("index"));
        //        verify(model, times(1)).addAttribute(eq("recipes"), recipeCaptor.capture());
        //        verify(recipeService, times(1)).getRecipes();
        //        Flux<Recipe> fluxInController = recipeCaptor.getValue();
        //        assertThat(fluxInController.count().block(), is(2L));
    }
}
