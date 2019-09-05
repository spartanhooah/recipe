package net.frey.recipe.bootstrap;

import net.frey.recipe.domain.Category;
import net.frey.recipe.domain.Difficulty;
import net.frey.recipe.domain.Ingredient;
import net.frey.recipe.domain.Recipe;
import net.frey.recipe.domain.UnitOfMeasure;
import net.frey.recipe.repository.CategoryRepository;
import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        UnitOfMeasure thing = unitOfMeasureRepository.findByDescription("Thing").get();
        UnitOfMeasure cup = unitOfMeasureRepository.findByDescription("Cup").get();
        UnitOfMeasure pint = unitOfMeasureRepository.findByDescription("Pint").get();
        UnitOfMeasure pound = unitOfMeasureRepository.findByDescription("Pound").get();

        Recipe chickenTacos = new Recipe();
        chickenTacos.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        chickenTacos.setCookTime(15);
        chickenTacos.setPrepTime(20);
        chickenTacos.setServings(5);
        chickenTacos.setSource("Simply Recipes");
        chickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");
        chickenTacos.setDifficulty(Difficulty.EASY);
//        chickenTacos.setImage(extractBytes("GrilledChickenTacos.jpg"));
        Category mexican = categoryRepository.findByDescription("Mexican").get();
        chickenTacos.getCategories().add(mexican);

        Ingredient anchoChiliPowder = new Ingredient();
        anchoChiliPowder.setDescription("ancho chili powder");
        anchoChiliPowder.setAmount(BigDecimal.valueOf(2));
        anchoChiliPowder.setUnitOfMeasure(tablespoon);
        anchoChiliPowder.setRecipe(chickenTacos);

        Ingredient oregano = new Ingredient();
        oregano.setDescription("dried oregano");
        oregano.setAmount(BigDecimal.ONE);
        oregano.setUnitOfMeasure(teaspoon);
        oregano.setRecipe(chickenTacos);

        Ingredient cumin = new Ingredient();
        cumin.setDescription("dried cumin");
        cumin.setAmount(BigDecimal.ONE);
        cumin.setUnitOfMeasure(teaspoon);
        cumin.setRecipe(chickenTacos);

        Ingredient sugar = new Ingredient();
        sugar.setDescription("sugar");
        sugar.setAmount(BigDecimal.ONE);
        sugar.setUnitOfMeasure(teaspoon);
        sugar.setRecipe(chickenTacos);

        Ingredient salt = new Ingredient();
        salt.setDescription("salt");
        salt.setAmount(BigDecimal.valueOf(0.5));
        salt.setUnitOfMeasure(tablespoon);
        salt.setRecipe(chickenTacos);

        Ingredient garlic = new Ingredient();
        garlic.setDescription("garlic clove, finely chopped");
        garlic.setAmount(BigDecimal.ONE);
        garlic.setUnitOfMeasure(thing);
        garlic.setRecipe(chickenTacos);

        Ingredient orangeZest = new Ingredient();
        orangeZest.setDescription("finely-grated orange zest");
        orangeZest.setAmount(BigDecimal.ONE);
        orangeZest.setUnitOfMeasure(tablespoon);
        orangeZest.setRecipe(chickenTacos);

        Ingredient orangeJuice = new Ingredient();
        orangeJuice.setDescription("fresh-squeezed orange juice");
        orangeJuice.setAmount(BigDecimal.valueOf(3L));
        orangeJuice.setUnitOfMeasure(tablespoon);
        orangeJuice.setRecipe(chickenTacos);

        Ingredient oliveOil = new Ingredient();
        oliveOil.setDescription("olive oil");
        oliveOil.setAmount(BigDecimal.valueOf(2L));
        oliveOil.setUnitOfMeasure(tablespoon);
        oliveOil.setRecipe(chickenTacos);

        Ingredient chicken = new Ingredient();
        chicken.setDescription("skinless, boneless chicken thighs");
        chicken.setAmount(BigDecimal.valueOf(5L));
        chicken.setUnitOfMeasure(thing);
        chicken.setRecipe(chickenTacos);

        Ingredient tortillas = new Ingredient();
        tortillas.setDescription("small corn tortillas");
        tortillas.setAmount(BigDecimal.valueOf(8L));
        tortillas.setUnitOfMeasure(thing);
        tortillas.setRecipe(chickenTacos);

        Ingredient arugula = new Ingredient();
        arugula.setDescription("packed baby arugula");
        arugula.setAmount(BigDecimal.valueOf(3L));
        arugula.setUnitOfMeasure(cup);
        arugula.setRecipe(chickenTacos);

        Ingredient avocado = new Ingredient();
        avocado.setDescription("medium ripe avocados");
        avocado.setAmount(BigDecimal.valueOf(2L));
        avocado.setUnitOfMeasure(thing);
        avocado.setRecipe(chickenTacos);

        Ingredient radish = new Ingredient();
        radish.setDescription("radishes, thinly sliced");
        radish.setAmount(BigDecimal.valueOf(4L));
        radish.setUnitOfMeasure(thing);
        radish.setRecipe(chickenTacos);

        Ingredient tomatoes = new Ingredient();
        tomatoes.setDescription("cherry tomatoes, halved");
        tomatoes.setAmount(BigDecimal.valueOf(0.5));
        tomatoes.setUnitOfMeasure(pint);
        tomatoes.setRecipe(chickenTacos);

        Ingredient redOnion = new Ingredient();
        redOnion.setDescription("red onion, thinly sliced");
        redOnion.setAmount(BigDecimal.valueOf(0.25));
        redOnion.setUnitOfMeasure(thing);
        redOnion.setRecipe(chickenTacos);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("roughly chopped cilantro");
        cilantro.setAmount(BigDecimal.ONE);
        cilantro.setUnitOfMeasure(thing);
        cilantro.setRecipe(chickenTacos);

        Ingredient sourCream = new Ingredient();
        sourCream.setDescription("sour cream, thinned with milk");
        sourCream.setAmount(BigDecimal.valueOf(0.5));
        sourCream.setUnitOfMeasure(cup);
        sourCream.setRecipe(chickenTacos);

        Ingredient milk = new Ingredient();
        milk.setDescription("milk");
        milk.setAmount(BigDecimal.valueOf(0.25));
        milk.setUnitOfMeasure(cup);
        milk.setRecipe(chickenTacos);

        Ingredient lime = new Ingredient();
        lime.setDescription("lime, cut into wedges");
        lime.setAmount(BigDecimal.ONE);
        lime.setUnitOfMeasure(thing);
        lime.setRecipe(chickenTacos);

        chickenTacos.getIngredients().addAll(Arrays.asList(
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
                lime
        ));
    }

    public byte[] extractBytes(String imageName) throws IOException {
        File fi = new File(imageName);
        return Files.readAllBytes(fi.toPath());
    }
}
