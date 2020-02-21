package net.frey.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.domain.Category;
import net.frey.recipe.domain.UnitOfMeasure;
import net.frey.recipe.repository.CategoryRepository;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class DataLoaderMySQL implements ApplicationListener<ContextRefreshedEvent> {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoaderMySQL(
            UnitOfMeasureRepository unitOfMeasureRepository,
            CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryRepository.count() == 0L) {
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L) {
            log.debug("Loading Units of Measure");
            loadUOM();
        }
    }

    private void loadCategories() {
        Category american = new Category();
        american.setDescription("American");
        categoryRepository.save(american);

        Category italian = new Category();
        italian.setDescription("Italian");
        categoryRepository.save(italian);

        Category mexican = new Category();
        mexican.setDescription("Mexican");
        categoryRepository.save(mexican);

        Category fastFood = new Category();
        fastFood.setDescription("Fast Food");
        categoryRepository.save(fastFood);
    }

    private void loadUOM() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure tablespoon = new UnitOfMeasure();
        tablespoon.setDescription("Tablespoon");
        unitOfMeasureRepository.save(tablespoon);

        UnitOfMeasure cup = new UnitOfMeasure();
        cup.setDescription("Cup");
        unitOfMeasureRepository.save(cup);

        UnitOfMeasure ounce = new UnitOfMeasure();
        ounce.setDescription("Ounce");
        unitOfMeasureRepository.save(ounce);

        UnitOfMeasure pinch = new UnitOfMeasure();
        pinch.setDescription("Pinch");
        unitOfMeasureRepository.save(pinch);

        UnitOfMeasure pint = new UnitOfMeasure();
        pint.setDescription("Pint");
        unitOfMeasureRepository.save(pint);

        UnitOfMeasure dash = new UnitOfMeasure();
        dash.setDescription("Dash");
        unitOfMeasureRepository.save(dash);

        UnitOfMeasure pound = new UnitOfMeasure();
        pound.setDescription("Pound");
        unitOfMeasureRepository.save(pound);

        UnitOfMeasure each = new UnitOfMeasure();
        each.setDescription("Each");
        unitOfMeasureRepository.save(each);
    }
}
