package net.frey.recipe.repository.reactive;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import net.frey.recipe.bootstrap.DataLoader;
import net.frey.recipe.domain.Category;
import net.frey.recipe.repository.CategoryRepository;
import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {
    @Autowired private UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private RecipeRepository recipeRepository;
    @Autowired private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    @Autowired private CategoryReactiveRepository categoryReactiveRepository;
    @Autowired private RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();
        unitOfMeasureReactiveRepository.deleteAll();
        categoryReactiveRepository.deleteAll();
        recipeReactiveRepository.deleteAll();

        DataLoader dataLoader =
                new DataLoader(
                        //                        recipeRepository,
                        //                        unitOfMeasureRepository,
                        //                        categoryRepository,
                        unitOfMeasureReactiveRepository,
                        categoryReactiveRepository,
                        recipeReactiveRepository);

        dataLoader.onApplicationEvent(null);
    }

    @Test
    public void roundTripTest() {
        Category input = new Category();
        input.setDescription("test");
        input.setId("1");

        categoryReactiveRepository.save(input).block();
        Flux<Category> results = categoryReactiveRepository.findAll();

        assertThat(
                results.any(category -> category.getDescription().equals("test")).block(),
                is(true));
    }
}
