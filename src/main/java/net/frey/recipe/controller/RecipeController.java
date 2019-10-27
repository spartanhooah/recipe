package net.frey.recipe.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class RecipeController {
    private RecipeService recipeService;

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/form";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + savedRecipeCommand.getId();
    }

    @PutMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/form";
    }

    @DeleteMapping("recipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        log.debug("Received DELETE request for " + id);
        recipeService.deleteById(id);

        return "redirect:/";
    }
}
