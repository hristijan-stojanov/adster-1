package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoriesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);

        model.addAttribute("bodyContent", "showCategories");
        return "master";
    }

    @GetMapping("/add-form")
    public String addCategory(Model model){
        List<Category> categories = this.categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "addCategory");

        return "master";
    }

    @GetMapping("/edit-form/{id}")
    public String editCategory(@PathVariable Long id,
                               Model model){

        Category category = this.categoryService.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        List<Category> categories = this.categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "addCategory");

        return "master";
    }

    @PostMapping("/add")
    public String addCategoryForm(@RequestParam (required = false) Long id,
                                  @RequestParam String name,
                                  @RequestParam (required = false) Long parentCategoryId){

        if (id != null){
            this.categoryService.edit(id, name, parentCategoryId);
        }
        else{
            this.categoryService.save(name, parentCategoryId);
        }

        return "redirect:/categories";
    }
}
