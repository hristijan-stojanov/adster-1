package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.ITEquipmentAdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ITEquipmentAd")
public class ITEquipmentsAdController {

    private final CategoryService categoryService;
    private final ITEquipmentAdService itEquipmentAdService;
    private final CityService cityService;

    public ITEquipmentsAdController(CategoryService categoryService, ITEquipmentAdService itEquipmentAdService, CityService cityService) {
        this.categoryService = categoryService;
        this.itEquipmentAdService = itEquipmentAdService;
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public String showITEquipmentAd(@PathVariable Long id, Model model) {

        ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", itEquipmentAd);
        model.addAttribute("bodyContent", "showAdsTemplates/showITEquipmentAd");
        return "master";
    }

    @GetMapping("/add-form")
    public String AddITEquipmentsAdPage(Model model) {

        Category category = this.categoryService.findCategoryByName("ITEquipment");
        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "adAdsTemplates/addITEquipmentAd");
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
            @RequestParam String cityName, //todo
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId, //todo
            @RequestParam(required = false) Long userId, //todo
            @RequestParam ITBrand brand,
            @RequestParam ProcessorBrand processor,
            @RequestParam String processorModel,
            @RequestParam TypeMemory typeMemory,
            @RequestParam int memorySize,
            @RequestParam int ramMemorySize
    ) {
        if (id != null) {
            this.itEquipmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize);
        } else {
            this.itEquipmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, userId, brand, processor, processorModel, typeMemory, memorySize,
                    ramMemorySize);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteITEquipmentsAd(@PathVariable Long id) {
        this.itEquipmentAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editITEquipmentsAd(@PathVariable Long id, Model model) {
        if (this.itEquipmentAdService.findById(id).isPresent()) {
            ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("itEquipmentAd", itEquipmentAd);
            model.addAttribute("bodyContent", "adsTemplates/addITEquipmentsAd");
            return "master";

        }
        return "redirect:/ads?error=AdNotFound";
    }
}
