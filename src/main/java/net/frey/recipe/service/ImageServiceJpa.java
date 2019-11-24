package net.frey.recipe.service;

import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceJpa implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceJpa(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(id);

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                recipe.setImage(byteObjects);

                recipeRepository.save(recipe);
            } else {
                throw new RuntimeException("Could not find recipe with ID " + id);
            }
        } catch (IOException e) {
            // TODO add exception handling
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
