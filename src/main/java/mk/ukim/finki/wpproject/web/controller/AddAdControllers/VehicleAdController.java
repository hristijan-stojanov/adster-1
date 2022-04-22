package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.VehicleAdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/VehicleAd")
public class VehicleAdController {

    private final CategoryService categoryService;
    private final VehicleAdService vehicleAdService;

    public VehicleAdController(CategoryService categoryService, VehicleAdService vehicleAdService) {
        this.categoryService = categoryService;
        this.vehicleAdService = vehicleAdService;
    }

    @GetMapping("/add-form/{categoryId}")
    public String AddVehicleAdPage(@PathVariable Long categoryId, Model model) {
        if (this.categoryService.findById(categoryId).isPresent()) {

            Category category = this.categoryService.findById(categoryId).get();
            model.addAttribute("category", category);
            model.addAttribute("bodyContent", "VehicleAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveVehicleAd(
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
            @RequestParam CarBrand brand,
            @RequestParam int yearMade,
            @RequestParam Color color,
            @RequestParam double milesTraveled,
            @RequestParam Fuel fuel,
            @RequestParam int enginePower,
            @RequestParam Gearbox gearbox,
            @RequestParam Registration registration
    ) {
        if (id != null) {
            this.vehicleAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration);
        } else {
            this.vehicleAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, userId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVehicleAd(@PathVariable Long id) {
        this.vehicleAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editVehicleAdPage(@PathVariable Long id, Model model) {
        if (this.vehicleAdService.findById(id).isPresent()) {
            VehicleAd vehicleAd = this.vehicleAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("vehicleAd", vehicleAd);
            model.addAttribute("bodyContent", "VehicleAd");
            return "master";

        }
        return "redirect:/ads?error=AdNotFound";
    }
}
