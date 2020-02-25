package net.frey.recipe.repository;

import net.frey.recipe.bootstrap.DataLoader;
import net.frey.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {
    @Autowired private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired private CategoryRepository categoryRepository;

    @Autowired private RecipeRepository recipeRepository;


    @Before
    public void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        DataLoader dataLoader = new DataLoader(recipeRepository, unitOfMeasureRepository, categoryRepository);

        dataLoader.onApplicationEvent(null);
    }

    @Test
    public void findTeaspoonByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertThat(uomOptional.get().getDescription(), is("Teaspoon"));
    }

    @Test
    public void findCupByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

        assertThat(uomOptional.get().getDescription(), is("Cup"));
    }
}
