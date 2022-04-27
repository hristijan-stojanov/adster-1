package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.UserService;
import mk.ukim.finki.wpproject.service.VehicleAdService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/VehicleAd")
public class VehicleAdController {

    private final CategoryService categoryService;
    private final VehicleAdService vehicleAdService;
    private final CityService cityService;
    private final UserService userService;

    public VehicleAdController(CategoryService categoryService, VehicleAdService vehicleAdService, CityService cityService, UserService userService) {
        this.categoryService = categoryService;
        this.vehicleAdService = vehicleAdService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showVehicleAd(@PathVariable Long id, Model model){

        VehicleAd vehicleAd = this.vehicleAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", vehicleAd);
        model.addAttribute("comments", vehicleAd.getComments());

        model.addAttribute("bodyContent", "showAdsTemplates/showVehicleAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddVehicleAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("Vehicle");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "adAdsTemplates/addVehicleAd");
        return "master";

    }

    @GetMapping("/add-form/{categoryId}")
    public String AddApartmentAdPage(@PathVariable Long categoryId, Model model) {

        if (this.categoryService.findById(categoryId).isPresent()){

            Category category = this.categoryService.findById(categoryId).get();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<CarBrand> carBrandList = Arrays.asList(CarBrand.values());
            List<Color> colorList = Arrays.asList(Color.values());
            List<Fuel> fuelList = Arrays.asList(Fuel.values());
            List<Gearbox> gearboxList = Arrays.asList(Gearbox.values());
            List<Registration> registrationList = Arrays.asList(Registration.values());

            model.addAttribute("category_1",category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList",adTypeList);
            model.addAttribute("conditionList",conditionList);
            model.addAttribute("carBrandList",carBrandList);
            model.addAttribute("colorList",colorList);
            model.addAttribute("fuelList",fuelList);
            model.addAttribute("gearboxList",gearboxList);
            model.addAttribute("registrationList",registrationList);

            model.addAttribute("bodyContent", "addAdsTemplates/addVehicleAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveVehicleAd(
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
            @RequestParam CarBrand brand,
            @RequestParam int yearMade,
            @RequestParam Color color,
            @RequestParam double milesTraveled,
            @RequestParam Fuel fuel,
            @RequestParam int enginePower,
            @RequestParam Gearbox gearbox,
            @RequestParam Registration registration,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (id != null) {
            this.vehicleAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration);
        } else {
            VehicleAd vehicleAd = this.vehicleAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, userId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration).orElseThrow(RuntimeException :: new);

            user.getAdvertisedAds().add(vehicleAd);
            this.userService.save(user);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVehicleAd(@PathVariable Long id) {
        this.vehicleAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editVehicleAdPage(@PathVariable Long id, Model model) {
        if (this.vehicleAdService.findById(id).isPresent()) {
            VehicleAd vehicleAd = this.vehicleAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("vehicleAd", vehicleAd);
            model.addAttribute("bodyContent", "adsTemplates/addVehicleAd");
            return "master";

        }
        return "redirect:/ads?error=AdNotFound";
    }
}
