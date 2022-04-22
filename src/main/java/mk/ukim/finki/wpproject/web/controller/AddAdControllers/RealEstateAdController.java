package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.RealEstateAdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/RealEstateAd")
public class RealEstateAdController {

    private final CategoryService categoryService;
    private final RealEstateAdService realEstateAdService;

    public RealEstateAdController(CategoryService categoryService, RealEstateAdService realEstateAdService) {
        this.categoryService = categoryService;
        this.realEstateAdService = realEstateAdService;
    }

    @GetMapping("/add-form/{categoryId}")
    public String AddRealEstateAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()) {
            Category category = this.categoryService.findById(categoryId).get();
            model.addAttribute("category", category);
            model.addAttribute("bodyContent", "adsTemplates/RealEstateAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveRealEstateAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityName,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
            @RequestParam int quadrature
    ) {
        if (id != null) {
            this.realEstateAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, quadrature);
        } else {
            this.realEstateAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, userId, quadrature);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRealEstateAd(@PathVariable Long id) {
        this.realEstateAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editRealEstateAd(@PathVariable Long id, Model model) {
        if (this.realEstateAdService.findById(id).isPresent()) {
            RealEstateAd realEstateAd = this.realEstateAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("realEstateAd", realEstateAd);
            model.addAttribute("bodyContent", "adsTemplates/RealEstateAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
