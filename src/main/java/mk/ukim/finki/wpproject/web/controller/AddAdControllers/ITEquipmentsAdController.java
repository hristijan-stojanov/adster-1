package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
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
@RequestMapping("/ITEquipmentAd")
public class ITEquipmentsAdController {

    private final CategoryService categoryService;
    private final ITEquipmentAdService itEquipmentAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public ITEquipmentsAdController(CategoryService categoryService, ITEquipmentAdService itEquipmentAdService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.itEquipmentAdService = itEquipmentAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showITEquipmentAd(@PathVariable Long id, Model model) {

        ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", itEquipmentAd);
        model.addAttribute("additionalContent", "showITEquipmentAd");

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
            List<ITBrand> itBrandList = Arrays.asList(ITBrand.values());
            List<ProcessorBrand> processorBrandList = Arrays.asList(ProcessorBrand.values());
            List<TypeMemory> typeMemoryList = Arrays.asList(TypeMemory.values());

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("itBrandList", itBrandList);
            model.addAttribute("processorBrandList", processorBrandList);
            model.addAttribute("typeMemoryList", typeMemoryList);

            model.addAttribute("bodyContent", "addAdsTemplates/addITEquipmentAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
    public String saveITEquipmentsAd(
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
            @RequestParam ITBrand brand,
            @RequestParam ProcessorBrand processor,
            @RequestParam String processorModel,
            @RequestParam TypeMemory typeMemory,
            @RequestParam int memorySize,
            @RequestParam int ramMemorySize,
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        if (id != null) {
            this.itEquipmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize);
        } else {
            ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityId, type, condition, categoryId, user.getId(), brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize).orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(itEquipmentAd);
            this.userService.save(user);

            imageService.addImagesToAd(itEquipmentAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editITEquipmentsAd(@PathVariable Long id, Model model) {
        if (this.itEquipmentAdService.findById(id).isPresent()) {

            ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).get();
            Category category = itEquipmentAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<ITBrand> itBrandList = Arrays.asList(ITBrand.values());
            List<ProcessorBrand> processorBrandList = Arrays.asList(ProcessorBrand.values());
            List<TypeMemory> typeMemoryList = Arrays.asList(TypeMemory.values());

            model.addAttribute("itEquipmentAd", itEquipmentAd);
            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("itBrandList", itBrandList);
            model.addAttribute("processorBrandList", processorBrandList);
            model.addAttribute("typeMemoryList", typeMemoryList);

            model.addAttribute("bodyContent", "addAdsTemplates/addITEquipmentAd");

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
                                 @RequestParam(required = false) ITBrand itBrand,
                                 @RequestParam(required = false) String model,
                                 @RequestParam(required = false) ProcessorBrand processorBrand,
                                 @RequestParam(required = false) String processorModel,
                                 @RequestParam(required = false) TypeMemory typeMemory,
                                 @RequestParam(required = false) Integer memorySizeFrom,
                                 @RequestParam(required = false) Integer memorySizeTo,
                                 @RequestParam(required = false) Integer ramMemorySizeFrom,
                                 @RequestParam(required = false) Integer ramMemorySizeTo,
                                 HttpServletRequest request) {

        List<Ad> filteredAds = itEquipmentAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, itBrand,
                model, processorBrand, processorModel, typeMemory, memorySizeFrom, memorySizeTo, ramMemorySizeFrom, ramMemorySizeTo);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
