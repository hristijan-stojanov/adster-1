package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.ClothesAdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ClothesAd")
public class ClothesAdController {

    private final CategoryService categoryService;
    private final ClothesAdService clothesAdService;

    public ClothesAdController(CategoryService categoryService, ClothesAdService clothesAdService) {
        this.categoryService = categoryService;
        this.clothesAdService = clothesAdService;
    }


    @GetMapping("/add-form")
    public String AddClothesAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Clothes");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "showAdsTemplates/showClothesAd");
        return "master";

    }

    @PostMapping("/add")
    public String saveClothesAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityId,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
            @RequestParam TypeClothing typeClothing,
            @RequestParam int numSize,
            @RequestParam Size size,
            @RequestParam Color color
    ) {
        if (id != null) {
            this.clothesAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, typeClothing, numSize, size, color);
        } else {
            this.clothesAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId, type,
                    condition, categoryId, userId, typeClothing, numSize, size, color);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClothesAd(@PathVariable Long id) {
        this.clothesAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editClothesAd(@PathVariable Long id, Model model) {
        if (this.clothesAdService.findById(id).isPresent()) {
            ClothesAd clothesAd = this.clothesAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("clothesAd", clothesAd);
            model.addAttribute("bodyContent", "adsTemplates/ClothesAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
