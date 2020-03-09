package net.frey.recipe.service;

import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.repository.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String id, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(id).block();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
                recipe.setImage(byteObjects);

                recipeRepository.save(recipe);
        } catch (IOException e) {
            // TODO add exception handling
            log.error("Error occurred", e);

            e.printStackTrace();
        }

        return Mono.empty();
    }

//    @Override
//    public Mono<Void> saveImageFile(String id, MultipartFile file) {
//        recipeRepository.findById(id).map(recipe -> {
//            Byte[] bytes;
//
//            try {
//                bytes = new Byte[file.getBytes().length];
//
//                int i = 0;
//
//                for (byte b : file.getBytes()) {
//                    bytes[i++] = b;
//                }
//
//                recipe.setImage(bytes);
//
//                return recipe;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("Something went wrong with bytes");
//            }
//        }).publish(recipeMono -> recipeRepository.save(recipeMono.block()));
//
//        return Mono.empty();
//    }
}
