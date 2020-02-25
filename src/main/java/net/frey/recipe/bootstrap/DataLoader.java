package net.frey.recipe.bootstrap;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.domain.Category;
import net.frey.recipe.domain.Difficulty;
import net.frey.recipe.domain.Ingredient;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.domain.UnitOfMeasure;
import net.frey.recipe.repository.CategoryRepository;
import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile("default")
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(
            RecipeRepository recipeRepository,
            UnitOfMeasureRepository unitOfMeasureRepository,
            CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadCategories();
        loadUnitsOfMeasure();
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>();

        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        UnitOfMeasure each = unitOfMeasureRepository.findByDescription("Each").get();
        UnitOfMeasure cup = unitOfMeasureRepository.findByDescription("Cup").get();
        UnitOfMeasure pint = unitOfMeasureRepository.findByDescription("Pint").get();
        UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();

        Category mexican = categoryRepository.findByDescription("Mexican").get();

        List<Category> currentCategories = new LinkedList<>();
        currentCategories.add(mexican);

        Recipe chickenTacos = new Recipe();
        chickenTacos.setTitle("Spicy grilled chicken tacos");
        chickenTacos.setDescription(
                "Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        chickenTacos.setCookTime(15);
        chickenTacos.setPrepTime(20);
        chickenTacos.setServings(5);
        chickenTacos.setCategories(currentCategories);
        chickenTacos.setSource("Simply Recipes");
        chickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacos.setDirections(
                "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
                        + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
                        + "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n"
                        + "Spicy Grilled Chicken Tacos\n"
                        + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
                        + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
                        + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
                        + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");
        chickenTacos.setDifficulty(Difficulty.MODERATE);
        //        chickenTacos.setImage(extractBytes("GrilledChickenTacos.jpg"));
        chickenTacos.getCategories().add(mexican);

        Ingredient anchoChiliPowder = new Ingredient();
        anchoChiliPowder.setDescription("ancho chili powder");
        anchoChiliPowder.setAmount(BigDecimal.valueOf(2));
        anchoChiliPowder.setUnitOfMeasure(tablespoon);

        Ingredient oregano = new Ingredient();
        oregano.setDescription("dried oregano");
        oregano.setAmount(BigDecimal.ONE);
        oregano.setUnitOfMeasure(teaspoon);

        Ingredient cumin = new Ingredient();
        cumin.setDescription("dried cumin");
        cumin.setAmount(BigDecimal.ONE);
        cumin.setUnitOfMeasure(teaspoon);

        Ingredient sugar = new Ingredient();
        sugar.setDescription("sugar");
        sugar.setAmount(BigDecimal.ONE);
        sugar.setUnitOfMeasure(teaspoon);

        Ingredient salt = new Ingredient();
        salt.setDescription("salt");
        salt.setAmount(BigDecimal.valueOf(0.5));
        salt.setUnitOfMeasure(tablespoon);

        Ingredient garlic = new Ingredient();
        garlic.setDescription("garlic clove, finely chopped");
        garlic.setAmount(BigDecimal.ONE);
        garlic.setUnitOfMeasure(each);

        Ingredient orangeZest = new Ingredient();
        orangeZest.setDescription("finely-grated orange zest");
        orangeZest.setAmount(BigDecimal.ONE);
        orangeZest.setUnitOfMeasure(tablespoon);

        Ingredient orangeJuice = new Ingredient();
        orangeJuice.setDescription("fresh-squeezed orange juice");
        orangeJuice.setAmount(BigDecimal.valueOf(3L));
        orangeJuice.setUnitOfMeasure(tablespoon);

        Ingredient oliveOil = new Ingredient();
        oliveOil.setDescription("olive oil");
        oliveOil.setAmount(BigDecimal.valueOf(2L));
        oliveOil.setUnitOfMeasure(tablespoon);

        Ingredient chicken = new Ingredient();
        chicken.setDescription("skinless, boneless chicken thighs");
        chicken.setAmount(BigDecimal.valueOf(5L));
        chicken.setUnitOfMeasure(each);

        Ingredient tortillas = new Ingredient();
        tortillas.setDescription("small corn tortillas");
        tortillas.setAmount(BigDecimal.valueOf(8L));
        tortillas.setUnitOfMeasure(each);

        Ingredient arugula = new Ingredient();
        arugula.setDescription("packed baby arugula");
        arugula.setAmount(BigDecimal.valueOf(3L));
        arugula.setUnitOfMeasure(cup);

        Ingredient avocado = new Ingredient();
        avocado.setDescription("medium ripe avocados");
        avocado.setAmount(BigDecimal.valueOf(2L));
        avocado.setUnitOfMeasure(each);

        Ingredient radish = new Ingredient();
        radish.setDescription("radishes, thinly sliced");
        radish.setAmount(BigDecimal.valueOf(4L));
        radish.setUnitOfMeasure(each);

        Ingredient tomatoes = new Ingredient();
        tomatoes.setDescription("cherry tomatoes, halved");
        tomatoes.setAmount(BigDecimal.valueOf(0.5));
        tomatoes.setUnitOfMeasure(pint);

        Ingredient redOnion = new Ingredient();
        redOnion.setDescription("red onion, thinly sliced");
        redOnion.setAmount(BigDecimal.valueOf(0.25));
        redOnion.setUnitOfMeasure(each);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("roughly chopped cilantro");
        cilantro.setAmount(BigDecimal.ONE);
        cilantro.setUnitOfMeasure(each);

        Ingredient sourCream = new Ingredient();
        sourCream.setDescription("sour cream, thinned with milk");
        sourCream.setAmount(BigDecimal.valueOf(0.5));
        sourCream.setUnitOfMeasure(cup);

        Ingredient milk = new Ingredient();
        milk.setDescription("milk");
        milk.setAmount(BigDecimal.valueOf(0.25));
        milk.setUnitOfMeasure(cup);

        Ingredient lime = new Ingredient();
        lime.setDescription("lime, cut into wedges");
        lime.setAmount(BigDecimal.ONE);
        lime.setUnitOfMeasure(each);

        chickenTacos
                .getIngredients()
                .addAll(
                        Arrays.asList(
                                anchoChiliPowder,
                                oregano,
                                cumin,
                                sugar,
                                salt,
                                garlic,
                                orangeZest,
                                orangeJuice,
                                oliveOil,
                                chicken,
                                tortillas,
                                arugula,
                                avocado,
                                radish,
                                tomatoes,
                                redOnion,
                                cilantro,
                                sourCream,
                                milk,
                                lime));

        recipeList.add(chickenTacos);

        Recipe guacamole = new Recipe();
        guacamole.setTitle("Guacamole");
        guacamole.setDescription(
                "Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");
        guacamole.setCookTime(0);
        guacamole.setPrepTime(10);
        guacamole.setServings(3);
        guacamole.setSource("Simply Recipes");
        guacamole.setCategories(currentCategories);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections(
                "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
                        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
                        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
                        + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
                        + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
                        + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
                        + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.getCategories().add(mexican);

        Ingredient kosherSalt = new Ingredient();
        kosherSalt.setDescription("Kosher salt");
        kosherSalt.setAmount(BigDecimal.valueOf(0.5));
        kosherSalt.setUnitOfMeasure(teaspoon);

        Ingredient juice = new Ingredient();
        juice.setDescription("fresh lime or lemon juice");
        juice.setAmount(BigDecimal.ONE);
        juice.setUnitOfMeasure(tablespoon);

        Ingredient onion = new Ingredient();
        onion.setDescription("minced red onion or thinly sliced green onion");
        onion.setAmount(BigDecimal.valueOf(3));
        onion.setUnitOfMeasure(tablespoon);

        Ingredient chili = new Ingredient();
        chili.setDescription("serrano chiles, stems and seeds removed, minced");
        chili.setAmount(BigDecimal.ONE);
        chili.setUnitOfMeasure(each);

        Ingredient cilantro2 = new Ingredient();
        cilantro2.setDescription("cilantro, finely chopped");
        cilantro2.setAmount(BigDecimal.valueOf(2));
        cilantro2.setUnitOfMeasure(tablespoon);

        Ingredient blackPepper = new Ingredient();
        blackPepper.setDescription("freshly grated black pepper");
        blackPepper.setAmount(BigDecimal.ONE);
        blackPepper.setUnitOfMeasure(dash);

        Ingredient tomato = new Ingredient();
        tomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
        tomato.setAmount(BigDecimal.valueOf(0.5));
        tomato.setUnitOfMeasure(each);

        Ingredient avocado2 = new Ingredient();
        avocado2.setDescription("ripe avocado");
        avocado2.setAmount(BigDecimal.valueOf(2));
        avocado2.setUnitOfMeasure(each);

        guacamole
                .getIngredients()
                .addAll(
                        Arrays.asList(
                                kosherSalt,
                                juice,
                                onion,
                                chili,
                                cilantro2,
                                blackPepper,
                                tomato,
                                avocado2));

        recipeList.add(guacamole);

        return recipeList;
    }

    private void loadUnitsOfMeasure() {
        UnitOfMeasure teaspoon = new UnitOfMeasure();
        teaspoon.setDescription("Teaspoon");
        unitOfMeasureRepository.save(teaspoon);

        UnitOfMeasure tablespoon = new UnitOfMeasure();
        tablespoon.setDescription("Tablespoon");
        unitOfMeasureRepository.save(tablespoon);

        UnitOfMeasure cup = new UnitOfMeasure();
        cup.setDescription("Cup");
        unitOfMeasureRepository.save(cup);

        UnitOfMeasure pinch = new UnitOfMeasure();
        pinch.setDescription("Pinch");
        unitOfMeasureRepository.save(pinch);

        UnitOfMeasure ounce = new UnitOfMeasure();
        ounce.setDescription("Ounce");
        unitOfMeasureRepository.save(ounce);

        UnitOfMeasure each = new UnitOfMeasure();
        each.setDescription("Each");
        unitOfMeasureRepository.save(each);

        UnitOfMeasure pint = new UnitOfMeasure();
        pint.setDescription("Pint");
        unitOfMeasureRepository.save(pint);

        UnitOfMeasure dash = new UnitOfMeasure();
        dash.setDescription("Dash");
        unitOfMeasureRepository.save(dash);
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

    public byte[] extractBytes(String imageName) throws IOException {
        File fi = new File(imageName);
        return Files.readAllBytes(fi.toPath());
    }
}
