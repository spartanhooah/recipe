package net.frey.recipe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.service.ImageService;
import net.frey.recipe.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    @GetMapping("/recipe/{recipeId}/imageform")
    public String getImageForm(@PathVariable String recipeId, @NotNull Model model) {
        log.debug("received request for image form");
        model.addAttribute("recipeId", recipeId);

        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String uploadImage(
            @PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        log.debug("received request to upload new image");
        imageService.saveImageFile(recipeId, file).block();

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/recipe/{recipeId}/image")
    public void getImage(@PathVariable String recipeId, HttpServletResponse response)
            throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();

        if (recipeCommand.getImage() != null) {
            log.debug("found image for recipe command with id " + recipeCommand.getId());

            byte[] bytes = new byte[recipeCommand.getImage().length];

            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()) {
                bytes[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytes);
            IOUtils.copy(is, response.getOutputStream());
        } else {
            log.debug("somehow didn't find image for recipe command with id " + recipeId);
        }
    }
}
