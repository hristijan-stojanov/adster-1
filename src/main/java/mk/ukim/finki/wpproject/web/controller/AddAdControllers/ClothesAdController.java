package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ClothesAd")
public class ClothesAdController {

    private final CategoryService categoryService;
    private final ClothesAdService clothesAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public ClothesAdController(CategoryService categoryService, ClothesAdService clothesAdService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.clothesAdService = clothesAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showClothesAd(@PathVariable Long id, Model model) {

        ClothesAd clothesAd = this.clothesAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", clothesAd);
        model.addAttribute("comments", clothesAd.getComments());
        model.addAttribute("additionalContent", "showClothesAd");

        model.addAttribute("bodyContent", "showAdDetails");
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
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

//        Integer intNumSize = null;
//        if (numSize != null && !numSize.isEmpty())
//            intNumSize = Integer.parseInt(numSize);

        if (id != null) {
            this.clothesAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, typeClothing, numSize, size, color);
        } else {
            ClothesAd clothesAd = this.clothesAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId, type,
                    condition, categoryId, user.getId(), typeClothing, numSize, size, color).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(clothesAd);
            this.userService.save(user);

            imageService.addImagesToAd(clothesAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editClothesAd(@PathVariable Long id, Model model) {
        if (this.clothesAdService.findById(id).isPresent()) {

            ClothesAd clothesAd = this.clothesAdService.findById(id).get();
            Category category = clothesAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<TypeClothing> typeClothingList = Arrays.asList(TypeClothing.values());
            List<Size> sizeList = Arrays.asList(Size.values());
            List<Color> colorList = Arrays.asList(Color.values());

            model.addAttribute("clothesAd", clothesAd);
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
        return "redirect:/ads?error=AdNotFound";
    }

    @GetMapping("/filter")
    public String getFilteredAds(@RequestParam(required = false) AdType type,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String cityId,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) Double priceFrom,
                                 @RequestParam(required = false) Double priceTo,
                                 @RequestParam(required = false) TypeClothing typeClothing,
                                 @RequestParam(required = false) Size size,
                                 @RequestParam(required = false) Color color,
                                 HttpServletRequest request) {

        List<Ad> filteredAds = clothesAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, typeClothing, size, color);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
