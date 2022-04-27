package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/OtherAd")
public class OtherAdController {

    private final CategoryService categoryService;
    private final AdService adService;
    private final CityService cityService;
    private final UserService userService;

    public OtherAdController(CategoryService categoryService, AdService adService, CityService cityService, UserService userService) {
        this.categoryService = categoryService;
        this.adService = adService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showOtherAd(@PathVariable Long id, Model model) {

        Ad otherAd = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", otherAd);
        model.addAttribute("bodyContent", "showAdsTemplates/showOtherAd");
        return "master";
    }

    @GetMapping("/add-form/{categoryId}")
    public String AddOtherAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()) {

            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);

            model.addAttribute("bodyContent", "addAdsTemplates/addOtherAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveOtherAd(
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
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.adService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId);
        } else {
            Ad ad = this.adService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, user.getId()).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(ad);
            this.userService.save(user);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOtherAd(@PathVariable Long id) {
        this.adService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.adService.findById(id).isPresent()) {
            Ad otherAd = this.adService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("otherAd", otherAd);
            model.addAttribute("bodyContent", "adsTemplates/addApartmentAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
