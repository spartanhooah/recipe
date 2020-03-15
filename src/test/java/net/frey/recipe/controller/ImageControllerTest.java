package net.frey.recipe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.service.ImageService;
import net.frey.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ImageControllerTest {
    @Mock private ImageService imageService;
    @Mock private RecipeService recipeService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ImageController imageController = new ImageController(imageService, recipeService);
        mockMvc =
                MockMvcBuilders.standaloneSetup(imageController)
                        .setControllerAdvice(new ControllerExceptionHandler())
                        .build();
    }

    @Test
    public void getImageForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        mockMvc.perform(get("/recipe/1/imageform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipeId"));
    }

    @Test
    public void postImage() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }

    //    @Test
    //    public void renderImageFromDb() throws Exception {
    //        RecipeCommand recipeCommand = new RecipeCommand();
    //        recipeCommand.setId("1");
    //
    //        String s = "fake image text";
    //        Byte[] bytesBoxed = new Byte[s.getBytes().length];
    //
    //        int i = 0;
    //
    //        for (byte primByte : s.getBytes()) {
    //            bytesBoxed[i++] = primByte;
    //        }
    //
    //        recipeCommand.setImage(bytesBoxed);
    //        Mono<RecipeCommand> recipeCommandMono = Mono.just(recipeCommand);
    //
    //        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommandMono);
    //
    //        MockHttpServletResponse response =
    //                mockMvc.perform(get("/recipe/1/image"))
    //                        .andExpect(status().isOk())
    //                        .andReturn()
    //                        .getResponse();
    //
    //        byte[] responseBytes = response.getContentAsByteArray();
    //
    //        assertThat(s.getBytes().length, is(responseBytes.length));
    //    }
}
