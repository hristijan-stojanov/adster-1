package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/add")
public class AddAdController {


    private final CategoryService categoryService;

    public AddAdController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String AddAdPage(Model model) {
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "select-category");
        return "master";
    }

    // TUKA NA POST IZGLEDA KE TREBA DA SE PRAVA NESTO
    // ZA SO KATEGORIJATA DA SE ODE NA KONKRETNA STRANA

}
