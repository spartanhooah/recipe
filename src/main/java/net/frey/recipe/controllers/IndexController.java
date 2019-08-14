package net.frey.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        System.out.println("Something else 55555 to say");
        return "index";
    }
}
