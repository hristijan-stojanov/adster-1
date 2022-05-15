package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/OtherAd")
public class OtherAdController {

    private final CategoryService categoryService;
    private final AdService adService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public OtherAdController(CategoryService categoryService, AdService adService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.adService = adService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showOtherAd(@PathVariable Long id, Model model) {

        Ad otherAd = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", otherAd);
        model.addAttribute("comments", otherAd.getComments());
        model.addAttribute("additionalContent", null);

        model.addAttribute("bodyContent", "showAdDetails");
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
            @RequestParam(required = false) boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityId,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        if (id != null) {
            this.adService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId);
        } else {
            Ad ad = this.adService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, user.getId()).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(ad);
            this.userService.save(user);

            imageService.addImagesToAd(ad.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.adService.findById(id).isPresent()) {

            Ad otherAd = this.adService.findById(id).get();
            Category category = otherAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());

            model.addAttribute("otherAd", otherAd);
            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);

            model.addAttribute("bodyContent", "addAdsTemplates/addOtherAd");

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
                                 HttpServletRequest request) {

//        List<Ad> filteredAds = this.adService.filter(title, cityId, categoryId);
//        List<Ad> additionalFilteredAds = adService.additionalFilter(priceFrom, priceTo);
//
//        filteredAds.retainAll(additionalFilteredAds);

        List<Ad> filteredAds = adService.filterList(type, title, cityId, categoryId, priceFrom, priceTo);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
