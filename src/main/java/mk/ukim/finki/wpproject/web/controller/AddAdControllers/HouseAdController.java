package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/HouseAd")
public class HouseAdController {

    private final CategoryService categoryService;
    private final HouseAdService houseAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public HouseAdController(CategoryService categoryService, HouseAdService houseAdService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.houseAdService = houseAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showHouseAd(@PathVariable Long id, Model model){

        HouseAd houseAd = this.houseAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", houseAd);
        model.addAttribute("comments", houseAd.getComments());

        model.addAttribute("bodyContent", "showAdsTemplates/showHouseAd");
        return "master";
    }

    @GetMapping("/add-form/{categoryId}")
    public String AddApartmentAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()){
            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<Heating> heatingList = Arrays.asList(Heating.values());

            model.addAttribute("category_1",category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList",adTypeList);
            model.addAttribute("conditionList",conditionList);
            model.addAttribute("heatingList",heatingList);

            model.addAttribute("bodyContent", "addAdsTemplates/addHouseAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveHouseAd(
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
            @RequestParam int yardArea,
            @RequestParam int numRooms,
            @RequestParam int numFloors,
            @RequestParam boolean hasBasement,
            @RequestParam Heating heating,
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.houseAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, quadrature, yearMade, yardArea, numRooms, numFloors, hasBasement, heating);
        } else {
            HouseAd houseAd = this.houseAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, userId, quadrature, yearMade, yardArea, numRooms, numFloors,
                    hasBasement, heating).orElseThrow(RuntimeException :: new);

            user.getAdvertisedAds().add(houseAd);
            this.userService.save(user);

            imageService.addImagesToAd(houseAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editHouseAd(@PathVariable Long id, Model model) {
        if (this.houseAdService.findById(id).isPresent()) {

            HouseAd houseAd = this.houseAdService.findById(id).get();
            Category category = houseAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<Heating> heatingList = Arrays.asList(Heating.values());

            model.addAttribute("category_1", category);
            model.addAttribute("houseAd", houseAd);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList",adTypeList);
            model.addAttribute("conditionList",conditionList);
            model.addAttribute("heatingList",heatingList);

            model.addAttribute("bodyContent", "addAdsTemplates/addHouseAd");

            return "master";
        }
        return "redirect:/ads?error=AdNotFound";
    }
}
