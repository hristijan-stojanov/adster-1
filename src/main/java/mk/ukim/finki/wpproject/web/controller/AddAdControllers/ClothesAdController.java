package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.ClothesAdService;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ClothesAd")
public class ClothesAdController {

    private final CategoryService categoryService;
    private final ClothesAdService clothesAdService;
    private final CityService cityService;
    private final UserService userService;

    public ClothesAdController(CategoryService categoryService, ClothesAdService clothesAdService, CityService cityService, UserService userService) {
        this.categoryService = categoryService;
        this.clothesAdService = clothesAdService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showClothesAd(@PathVariable Long id, Model model) {

        ClothesAd clothesAd = this.clothesAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", clothesAd);
        model.addAttribute("bodyContent", "showAdsTemplates/showClothesAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddClothesAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Clothes");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "adAdsTemplates/addClothesAd");
        return "master";

    }

    @GetMapping("/add-form/{categoryId}")
    public String AddApartmentAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()) {
            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<TypeClothing> typeClothingList = Arrays.asList(TypeClothing.values());
            List<Size> sizeList = Arrays.asList(Size.values());
            List<Color> colorList = Arrays.asList(Color.values());

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("typeClothingList", typeClothingList);
            model.addAttribute("sizeList", sizeList);
            model.addAttribute("colorList", colorList);

            model.addAttribute("bodyContent", "addAdsTemplates/addClothesAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
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
            @RequestParam TypeClothing typeClothing,
            @RequestParam int numSize,
            @RequestParam Size size,
            @RequestParam Color color,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.clothesAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, typeClothing, numSize, size, color);
        } else {
            ClothesAd clothesAd = this.clothesAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId, type,
                    condition, categoryId, userId, typeClothing, numSize, size, color).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(clothesAd);
            this.userService.save(user);
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
            model.addAttribute("bodyContent", "adsTemplates/addClothesAd");
            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
