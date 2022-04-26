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
    private final AdService adService;

    public AddAdController(CategoryService categoryService, AdService adService) {
        this.categoryService = categoryService;
        this.adService = adService;
    }

    @GetMapping
    public String AddAdPage(Model model) {
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "select-category");
        return "master";
    }

    @GetMapping("/{id}")
    public String SelectCategory(@PathVariable Long id){
        return "redirect:/"+this.adService.redirectAdBasedOnCategory(id)+"/add-form/"+id.toString();
    }

}
