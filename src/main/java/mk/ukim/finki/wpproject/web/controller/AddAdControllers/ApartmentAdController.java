package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Genre;
import mk.ukim.finki.wpproject.model.enums.Heating;
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
@RequestMapping(value = "/ApartmentAd")
public class ApartmentAdController {

    private final CategoryService categoryService;
    private final ApartmentAdService apartmentAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public ApartmentAdController(CategoryService categoryService, ApartmentAdService apartmentAdService,
                                 CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.apartmentAdService = apartmentAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showApartmentAd(@PathVariable Long id, Model model) {

        ApartmentAd apartmentAd = this.apartmentAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", apartmentAd);
        model.addAttribute("comments", apartmentAd.getComments());
        model.addAttribute("additionalContent", "showApartmentAd");

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
            @RequestParam(required = false) boolean isDeliveryPossible,
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
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        if (id != null) {
            this.apartmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement, hasElevator,
                    hasParkingSpot, heating);
        } else {
            ApartmentAd apartmentAd = this.apartmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, user.getId(), quadrature, yearMade, numRooms, numFloors, floor, hasBasement,
                    hasElevator, hasParkingSpot, heating).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(apartmentAd);
            this.userService.save(user);

            imageService.addImagesToAd(apartmentAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.apartmentAdService.findById(id).isPresent()) {

            ApartmentAd apartmentAd = this.apartmentAdService.findById(id).get();
            Category category = apartmentAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<Heating> heatingList = Arrays.asList(Heating.values());

            model.addAttribute("apartmentAd", apartmentAd);
            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("heatingList", heatingList);

            model.addAttribute("bodyContent", "addAdsTemplates/addApartmentAd");

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
                                 @RequestParam(required = false) Integer yearMadeFrom,
                                 @RequestParam(required = false) Integer yearMadeTo,
                                 @RequestParam(required = false) Integer numRoomsFrom,
                                 @RequestParam(required = false) Integer numRoomsTo,
                                 @RequestParam(required = false) Integer floorFrom,
                                 @RequestParam(required = false) Integer floorTo,
                                 @RequestParam(required = false) Boolean hasBasement,
                                 @RequestParam(required = false) Boolean hasElevator,
                                 @RequestParam(required = false) Boolean hasParkingSpot,
                                 @RequestParam(required = false) Heating heating,
                                 HttpServletRequest request) {

        List<Ad> filteredAds = apartmentAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, quadratureFrom, quadratureTo,
                yearMadeFrom, yearMadeTo, numRoomsFrom, numRoomsTo, floorFrom, floorTo, hasBasement, hasElevator, hasParkingSpot, heating);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
