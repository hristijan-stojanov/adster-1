package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ApartmentAd")
public class ApartmentAdController {

    private final CategoryService categoryService;
    private final ApartmentAdService apartmentAdService;

    public ApartmentAdController(CategoryService categoryService, ApartmentAdService apartmentAdService) {
        this.categoryService = categoryService;
        this.apartmentAdService = apartmentAdService;
    }

    @GetMapping("/{id}")
    public String showApartmentAd(@PathVariable Long id, Model model){

        ApartmentAd apartmentAd = this.apartmentAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", apartmentAd);
        model.addAttribute("bodyContent", "showAdsTemplates/showApartmentAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddApartmentAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Apartment");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "adAdsTemplates/ApartmentAd");
        return "master";

    }

    @PostMapping("/add")
    public String saveApartmentAd(
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
            @RequestParam int quadrature,
            @RequestParam int yearMade,
            @RequestParam int numRooms,
            @RequestParam int numFloors,
            @RequestParam int floor,
            @RequestParam boolean hasBasement,
            @RequestParam boolean hasElevator,
            @RequestParam boolean hasParkingSpot,
            @RequestParam Heating heating
    ) {
        if (id != null) {
            this.apartmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement, hasElevator,
                    hasParkingSpot, heating);
        } else {
            this.apartmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, userId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement,
                    hasElevator, hasParkingSpot, heating);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteApartmentAd(@PathVariable Long id) {
        this.apartmentAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.apartmentAdService.findById(id).isPresent()) {
            ApartmentAd apartmentAd = this.apartmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("apartmentAd", apartmentAd);
            model.addAttribute("bodyContent", "adsTemplates/ApartmentAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
