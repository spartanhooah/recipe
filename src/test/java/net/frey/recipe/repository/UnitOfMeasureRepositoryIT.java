package net.frey.recipe.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import net.frey.recipe.bootstrap.DataLoader;
import net.frey.recipe.domain.UnitOfMeasure;
import net.frey.recipe.repository.reactive.CategoryReactiveRepository;
import net.frey.recipe.repository.reactive.RecipeReactiveRepository;
import net.frey.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {
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
    public void findCupByDescription() {
        Mono<UnitOfMeasure> unitOfMeasureMono =
                unitOfMeasureReactiveRepository.findByDescription("Cup");

        assertThat(unitOfMeasureMono.block().getDescription(), is("Cup"));
    }

    @Test
    public void findTeaspoonByDescription() {
        Mono<UnitOfMeasure> unitOfMeasureMono =
                unitOfMeasureReactiveRepository.findByDescription("Teaspoon");

        assertThat(unitOfMeasureMono.block().getDescription(), is("Teaspoon"));
    }
}
