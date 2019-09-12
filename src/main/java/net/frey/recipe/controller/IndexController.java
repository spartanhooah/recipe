package net.frey.recipe.controller;

import net.frey.recipe.repository.RecipeRepository;
import net.frey.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private RecipeService recipeService;
    private RecipeRepository recipeRepository;

    public IndexController(RecipeService recipeService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
//        model.addAttribute("recipes", recipeService.getRecipes());
        model.addAttribute("recipes", recipeRepository.findAll());

        return "index";
    }
}
