package net.frey.recipe.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.reactive.RecipeReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {
    @Mock private RecipeReactiveRepository recipeRepository;

    @InjectMocks private ImageServiceImpl imageService;

    @Test
    public void saveImageFile() throws IOException {
        String id = "1";
        MultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        //        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = forClass(Recipe.class);

        imageService.saveImageFile(id, multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertThat(multipartFile.getBytes().length, is(savedRecipe.getImage().length));
    }
}
