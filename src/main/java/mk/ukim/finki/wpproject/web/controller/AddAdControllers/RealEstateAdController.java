package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Genre;
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
@RequestMapping("/RealEstateAd")
public class RealEstateAdController {

    private final CategoryService categoryService;
    private final RealEstateAdService realEstateAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public RealEstateAdController(CategoryService categoryService, RealEstateAdService realEstateAdService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.realEstateAdService = realEstateAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showRealEstateAd(@PathVariable Long id, Model model) {

        RealEstateAd realEstateAd = this.realEstateAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", realEstateAd);
        model.addAttribute("comments", realEstateAd.getComments());
        model.addAttribute("additionalContent", "showRealEstateAd");

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

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);

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
            @RequestParam(required = false) boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityId,
            @RequestParam AdType type,
            @RequestParam(required = false) Condition condition,
            @RequestParam Long categoryId,
            @RequestParam int quadrature,
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        if (id != null) {
            this.realEstateAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, quadrature);
        } else {
            RealEstateAd realEstateAd = this.realEstateAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, user.getId(), quadrature).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(realEstateAd);
            this.userService.save(user);

            imageService.addImagesToAd(realEstateAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editRealEstateAd(@PathVariable Long id, Model model) {
        if (this.realEstateAdService.findById(id).isPresent()) {

            RealEstateAd realEstateAd = this.realEstateAdService.findById(id).get();
            Category category = realEstateAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());

            model.addAttribute("category_1", category);
            model.addAttribute("realEstateAd", realEstateAd);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);

            model.addAttribute("bodyContent", "addAdsTemplates/addRealEstateAd");

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
                                 @RequestParam(required = false) Integer quadratureFrom,
                                 @RequestParam(required = false) Integer quadratureTo,
                                 HttpServletRequest request) {

        List<Ad> filteredAds = realEstateAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, quadratureFrom, quadratureTo);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
