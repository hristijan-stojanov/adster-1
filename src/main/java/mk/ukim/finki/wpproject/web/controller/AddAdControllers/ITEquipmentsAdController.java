package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.ITEquipmentAdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ITEquipmentsAd")
public class ITEquipmentsAdController {

    private final CategoryService categoryService;
    private final ITEquipmentAdService itEquipmentAdService;

    public ITEquipmentsAdController(CategoryService categoryService, ITEquipmentAdService itEquipmentAdService) {
        this.categoryService = categoryService;
        this.itEquipmentAdService = itEquipmentAdService;
    }

    @GetMapping("/add-form/{categoryId}")
    public String AddITEquipmentsAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()) {
            Category category = this.categoryService.findById(categoryId).get();
            model.addAttribute("category", category);
            model.addAttribute("bodyContent", "adsTemplates/ITEquipmentsAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveITEquipmentsAd(
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
            @RequestParam ITBrand brand,
            @RequestParam ProcessorBrand processor,
            @RequestParam String processorModel,
            @RequestParam TypeMemory typeMemory,
            @RequestParam int memorySize,
            @RequestParam int ramMemorySize
    ) {
        if (id != null) {
            this.itEquipmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize);
        } else {
            this.itEquipmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, userId, brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteITEquipmentsAd(@PathVariable Long id) {
        this.itEquipmentAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editITEquipmentsAd(@PathVariable Long id, Model model) {
        if (this.itEquipmentAdService.findById(id).isPresent()) {
            ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("itEquipmentAd", itEquipmentAd);
            model.addAttribute("bodyContent", "adsTemplates/ITEquipmentsAd");
            return "master";

        }
        return "redirect:/ads?error=AdNotFound";
    }
}
