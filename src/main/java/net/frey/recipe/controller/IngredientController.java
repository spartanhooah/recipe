package net.frey.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.IngredientCommand;
import net.frey.recipe.service.IngredientService;
import net.frey.recipe.service.RecipeService;
import net.frey.recipe.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(path = "/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping(path = "/recipe/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredient(
            @PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        model.addAttribute(
                "ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping(path = "/recipe/{recipeId}/ingredients/{ingredientId}/update")
    public String getIngredientForm(
            @PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        model.addAttribute(
                "ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("unitOfMeasureList", unitOfMeasureService.listAllUnitsOfMeasure());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping(path = "/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredientCommand(ingredientCommand);

        Long recipeId = savedIngredientCommand.getRecipeId();
        Long ingredientCommandId = savedIngredientCommand.getId();
        log.debug("Saved recipe ID: {}", recipeId);
        log.debug("Saved ingredient ID: {}", ingredientCommandId);

        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientCommandId + "/show";
    }
}