package net.frey.recipe.controller;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.IngredientCommand;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.service.IngredientService;
import net.frey.recipe.service.RecipeService;
import net.frey.recipe.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, @NotNull Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(
            @PathVariable String recipeId,
            @PathVariable String ingredientId,
            @NotNull Model model) {
        log.info("showing ingredient with id " + ingredientId);
        model.addAttribute(
                "ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForm(
            @PathVariable String recipeId,
            @PathVariable String ingredientId,
            @NotNull Model model) {
        model.addAttribute(
                "ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        model.addAttribute(
                "unitOfMeasureList",
                unitOfMeasureService.listAllUnitsOfMeasure());

        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{recipeId}/ingredients")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredientCommand(ingredientCommand).block();

        String recipeId = savedIngredientCommand.getRecipeId();
        String ingredientCommandId = savedIngredientCommand.getId();
        log.debug("Saved recipe ID: {}", recipeId);
        log.debug("Saved ingredient ID: {}", ingredientCommandId);

        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientCommandId + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipeForm(@PathVariable String recipeId, @NotNull Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
        // TODO: throw exception if recipeCommand is null
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute(
                "unitOfMeasureList",
                unitOfMeasureService.listAllUnitsOfMeasure());

        return "recipe/ingredient/form";
    }

    @DeleteMapping("recipe/{recipeId}/ingredient/{ingredientId}")
    public String deleteIngredient(
            @PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(ingredientId).block();

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
