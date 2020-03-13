package net.frey.recipe.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.RecipeCommand;
import net.frey.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Controller
@AllArgsConstructor
public class RecipeController {
    private static final String FORM_URL = "recipe/form";
    private RecipeService recipeService;

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, @NotNull Model model) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String getRecipeForm(@NotNull Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return FORM_URL;
    }

    @PostMapping("recipe")
    public String saveRecipe(
            @Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return FORM_URL;
        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand).block();

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipeForm(@PathVariable String id, @NotNull Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id).block());

        return FORM_URL;
    }

    @DeleteMapping("recipe/{id}")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Received DELETE request for " + id);
        recipeService.deleteById(id);

        return "redirect:/";
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception e) {
//        log.error("Handling not-found exception: " + e.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("404error");
//        modelAndView.addObject("exception", e);
//
//        return modelAndView;
//    }
}
