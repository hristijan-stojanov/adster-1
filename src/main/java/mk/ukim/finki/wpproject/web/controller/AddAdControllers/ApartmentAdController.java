package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ApartmentAd")
public class ApartmentAdController {

    private final CategoryService categoryService;
    private final ApartmentAdService apartmentAdService;
    private final CityService cityService;
    private final UserService userService;

    public ApartmentAdController(CategoryService categoryService, ApartmentAdService apartmentAdService,
                                 CityService cityService, UserService userService) {
        this.categoryService = categoryService;
        this.apartmentAdService = apartmentAdService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showApartmentAd(@PathVariable Long id, Model model) {

        ApartmentAd apartmentAd = this.apartmentAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", apartmentAd);
        model.addAttribute("bodyContent", "showAdsTemplates/showApartmentAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddApartmentAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Apartment");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "addAdsTemplates/addApartmentAd");
        return "master";

    }

    @GetMapping("/add-form/{categoryId}")
    public String AddApartmentAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()) {

            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<Heating> heatingList = Arrays.asList(Heating.values());

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("heatingList", heatingList);

            model.addAttribute("bodyContent", "addAdsTemplates/addApartmentAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
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
            @RequestParam int quadrature,
            @RequestParam int yearMade,
            @RequestParam int numRooms,
            @RequestParam int numFloors,
            @RequestParam int floor,
            @RequestParam boolean hasBasement,
            @RequestParam boolean hasElevator,
            @RequestParam boolean hasParkingSpot,
            @RequestParam Heating heating,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.apartmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement, hasElevator,
                    hasParkingSpot, heating);
        } else {
            ApartmentAd apartmentAd = this.apartmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, user.getId(), quadrature, yearMade, numRooms, numFloors, floor, hasBasement,
                    hasElevator, hasParkingSpot, heating).orElseThrow(RuntimeException :: new);

            user.getAdvertisedAds().add(apartmentAd);
            this.userService.save(user);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteApartmentAd(@PathVariable Long id) {
        this.apartmentAdService.deleteById(id);
        return "redirect:/ads";
    }


    // tuka ne treba da moze da mene kategorija
    @GetMapping("/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.apartmentAdService.findById(id).isPresent()) {
            ApartmentAd apartmentAd = this.apartmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("apartmentAd", apartmentAd);
            model.addAttribute("bodyContent", "adsTemplates/addApartmentAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
