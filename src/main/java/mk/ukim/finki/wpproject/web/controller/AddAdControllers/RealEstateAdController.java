package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.RealEstateAdService;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/RealEstateAd")
public class RealEstateAdController {

    private final CategoryService categoryService;
    private final RealEstateAdService realEstateAdService;
    private final CityService cityService;
    private final UserService userService;

    public RealEstateAdController(CategoryService categoryService, RealEstateAdService realEstateAdService, CityService cityService, UserService userService) {
        this.categoryService = categoryService;
        this.realEstateAdService = realEstateAdService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showRealEstateAd(@PathVariable Long id, Model model){

        RealEstateAd realEstateAd = this.realEstateAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", realEstateAd);
        model.addAttribute("comments", realEstateAd.getComments());

        model.addAttribute("bodyContent", "showAdsTemplates/showRealEstateAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddRealEstateAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Real Estate");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "adAdsTemplates/addRealEstateAd");
        return "master";

    }

    @GetMapping("/add-form/{categoryId}")
    public String AddApartmentAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()){

            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());

            model.addAttribute("category_1",category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList",adTypeList);
            model.addAttribute("conditionList",conditionList);

            model.addAttribute("bodyContent", "addAdsTemplates/addRealEstateAd");
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
            @RequestParam String cityId,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam int quadrature,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.realEstateAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, quadrature);
        } else {
            RealEstateAd realEstateAd = this.realEstateAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, userId, quadrature).orElseThrow(RuntimeException :: new);

            user.getAdvertisedAds().add(realEstateAd);
            this.userService.save(user);
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
            model.addAttribute("bodyContent", "adsTemplates/addRealEstateAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
