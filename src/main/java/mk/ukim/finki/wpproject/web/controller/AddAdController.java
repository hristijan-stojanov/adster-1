package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.ads.BookAd;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/add")
public class AddAdController {

    private final ApartmentAdService apartmentAdService;
    private final BookAdService bookAdService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final ClothesAdService clothesAdService;
    private final HouseAdService houseAdService;
    private final ITEquipmentAdService itEquipmentAdService;
    private final RealEstateAdService realEstateAdService;
    private final UserService userService;
    private final VehicleAdService vehicleAdService;

    public AddAdController(ApartmentAdService apartmentAdService, BookAdService bookAdService, CategoryService categoryService,
                           CityService cityService, ClothesAdService clothesAdService, HouseAdService houseAdService,
                           ITEquipmentAdService itEquipmentAdService, RealEstateAdService realEstateAdService,
                           UserService userService, VehicleAdService vehicleAdService) {
        this.apartmentAdService = apartmentAdService;
        this.bookAdService = bookAdService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.clothesAdService = clothesAdService;
        this.houseAdService = houseAdService;
        this.itEquipmentAdService = itEquipmentAdService;
        this.realEstateAdService = realEstateAdService;
        this.userService = userService;
        this.vehicleAdService = vehicleAdService;
    }


    @GetMapping
    public String AddAdPage(Model model) {
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "select-category");
        return "master-template";
    }

    // ------------------------APARTMENT--------------------------------------------------------------------------------

    @GetMapping("/ApartmentAd")
    public String AddApartmentAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "ApartmentAd");
        return "master-template";
    }

    @PostMapping("/ApartmentAd")
    public String saveApartmentAd(
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
            @RequestParam(required = false) Long userId,
            @RequestParam int quadrature,
            @RequestParam int yearMade,
            @RequestParam int numRooms,
            @RequestParam int numFloors,
            @RequestParam int floor,
            @RequestParam boolean hasBasement,
            @RequestParam boolean hasElevator,
            @RequestParam boolean hasParkingSpot,
            @RequestParam Heating heating
    ) {
        if (id != null) {
            this.apartmentAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement, hasElevator,
                    hasParkingSpot, heating);
        } else {
            this.apartmentAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, userId, quadrature, yearMade, numRooms, numFloors, floor, hasBasement,
                    hasElevator, hasParkingSpot, heating);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/ApartmentAd/delete/{id}")
    public String deleteApartmentAd(@PathVariable Long id) {
        this.apartmentAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/ApartmentAd/edit-form/{id}")
    public String editApartmentAd(@PathVariable Long id, Model model) {
        if (this.apartmentAdService.findById(id).isPresent()) {
            ApartmentAd apartmentAd = this.apartmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("apartmentAd", apartmentAd);
            model.addAttribute("bodyContent", "ApartmentAd");
            return "master-template";
        }
        return "redirect:/ads?error=AdNotFound";
    }


    // ------------------------------HOUSE------------------------------------------------------------------------------

    @GetMapping("/HouseAd")
    public String AddHousePage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "HouseAd");
        return "master-template";
    }

    @PostMapping("/HouseAd")
    public String saveHouseAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityName,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
            @RequestParam int quadrature,
            @RequestParam int yearMade,
            @RequestParam int yardArea,
            @RequestParam int numRooms,
            @RequestParam int numFloors,
            @RequestParam boolean hasBasement,
            @RequestParam Heating heating
    ) {
        if (id != null) {
            this.houseAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, quadrature, yearMade, yardArea, numRooms, numFloors, hasBasement, heating);
        } else {
            this.houseAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, userId, quadrature, yearMade, yardArea, numRooms, numFloors,
                    hasBasement, heating);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/HouseAd/delete/{id}")
    public String deleteHouseAd(@PathVariable Long id) {
        this.houseAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/HouseAd/edit-form/{id}")
    public String editHouseAd(@PathVariable Long id, Model model) {
        if (this.houseAdService.findById(id).isPresent()) {
            HouseAd houseAd = this.houseAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("houseAd", houseAd);
            model.addAttribute("bodyContent", "HouseAd");
            return "master-template";
        }
        return "redirect:/ads?error=AdNotFound";
    }


    // ------------------------------BOOK-------------------------------------------------------------------------------

    @GetMapping("/BookAd")
    public String AddBookAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "BookAd");
        return "master-template";
    }


    @PostMapping("/BookAd")
    public String saveBookAd(
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
            @RequestParam(required = false) Long userId,
            @RequestParam String author,
            @RequestParam int yearMade,
            @RequestParam int numPages,
            @RequestParam Genre genre
    ) {
        if (id != null) {
            this.bookAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible,
                    price, cityId, type, condition, categoryId, author, yearMade, numPages, genre);
        } else {
            this.bookAdService.save(title, description, isExchangePossible, isDeliveryPossible,
                    price, cityId, type, condition, categoryId, userId, author, yearMade, numPages, genre);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/BookAd/delete/{id}")
    public String deleteBookAd(@PathVariable Long id) {
        this.bookAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/BookAd/edit-form/{id}")
    public String editBookAd(@PathVariable Long id, Model model) {
        if (this.bookAdService.findById(id).isPresent()) {
            BookAd bookAd = this.bookAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("bookAd", bookAd);
            model.addAttribute("bodyContent", "BookAd");
            return "master-template";
        }
        return "redirect:/ads?error=AdNotFound";
    }


    // -----------------------REAL_ESTATE-------------------------------------------------------------------------------

    @GetMapping("/RealEstateAd")
    public String AddRealEstateAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "RealEstateAd");
        return "master-template";
    }

    @PostMapping("/RealEstateAd")
    public String saveRealEstateAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityName,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
            @RequestParam int quadrature
    ) {
        if (id != null) {
            this.realEstateAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, quadrature);
        } else {
            this.realEstateAdService.save(title, description, isExchangePossible, isDeliveryPossible, price,
                    cityName, type, condition, categoryId, userId, quadrature);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/RealEstateAd/delete/{id}")
    public String deleteRealEstateAd(@PathVariable Long id) {
        this.realEstateAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/RealEstateAd/edit-form/{id}")
    public String editRealEstateAd(@PathVariable Long id, Model model) {
        if (this.realEstateAdService.findById(id).isPresent()) {
            RealEstateAd realEstateAd = this.realEstateAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("realEstateAd", realEstateAd);
            model.addAttribute("bodyContent", "RealEstateAd");
            return "master-template";
        }
        return "redirect:/ads?error=AdNotFound";
    }

    // -----------------------CLOTHES-----------------------------------------------------------------------------------

    @GetMapping("/ClothesAd")
    public String AddClothesAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "ClothesAd");
        return "master-template";
    }

    @PostMapping("/ClothesAd")
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
            @RequestParam(required = false) Long userId,
            @RequestParam TypeClothing typeClothing,
            @RequestParam int numSize,
            @RequestParam Size size,
            @RequestParam Color color
    ) {
        if (id != null) {
            this.clothesAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityId,
                    type, condition, categoryId, typeClothing, numSize, size, color);
        } else {
            this.clothesAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityId, type,
                    condition, categoryId, userId, typeClothing, numSize, size, color);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/ClothesAd/delete/{id}")
    public String deleteClothesAd(@PathVariable Long id) {
        this.clothesAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/ClothesAd/edit-form/{id}")
    public String editClothesAd(@PathVariable Long id, Model model) {
        if (this.clothesAdService.findById(id).isPresent()) {
            ClothesAd clothesAd = this.clothesAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("clothesAd", clothesAd);
            model.addAttribute("bodyContent", "ClothesAd");
            return "master-template";
        }
        return "redirect:/ads?error=AdNotFound";
    }

    // -----------------------ITEquipments------------------------------------------------------------------------------

    @GetMapping("/ITEquipmentsAd")
    public String AddITEquipmentsAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "ITEquipmentsAd");
        return "master-template";
    }

    @PostMapping("/ITEquipmentsAd")
    public String saveITEquipmentsAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityName,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
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

    @DeleteMapping("/ITEquipmentsAd/delete/{id}")
    public String deleteITEquipmentsAd(@PathVariable Long id) {
        this.itEquipmentAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/ITEquipmentsAd/edit-form/{id}")
    public String editITEquipmentsAd(@PathVariable Long id, Model model) {
        if (this.itEquipmentAdService.findById(id).isPresent()) {
            ITEquipmentAd itEquipmentAd = this.itEquipmentAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("itEquipmentAd", itEquipmentAd);
            model.addAttribute("bodyContent", "ITEquipmentsAd");
            return "master-template";

        }
        return "redirect:/ads?error=AdNotFound";
    }


    // -----------------------VEHICLE-----------------------------------------------------------------------------------

    @GetMapping("/VehicleAd")
    public String AddVehicleAdPage(Model model, Long categoryId) {
        Category category = this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        model.addAttribute("category", category);
        model.addAttribute("bodyContent", "VehicleAd");
        return "master-template";
    }

    @PostMapping("/VehicleAd")
    public String saveVehicleAd(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam boolean isExchangePossible,
            @RequestParam boolean isDeliveryPossible,
            @RequestParam Double price,
            @RequestParam String cityName,
            @RequestParam AdType type,
            @RequestParam Condition condition,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long userId,
            @RequestParam CarBrand brand,
            @RequestParam int yearMade,
            @RequestParam Color color,
            @RequestParam double milesTraveled,
            @RequestParam Fuel fuel,
            @RequestParam int enginePower,
            @RequestParam Gearbox gearbox,
            @RequestParam Registration registration
    ) {
        if (id != null) {
            this.vehicleAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration);
        } else {
            this.vehicleAdService.save(title, description, isExchangePossible, isDeliveryPossible, price, cityName,
                    type, condition, categoryId, userId, brand, yearMade, color, milesTraveled, fuel, enginePower, gearbox,
                    registration);
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/VehicleAd/delete/{id}")
    public String deleteVehicleAd(@PathVariable Long id) {
        this.vehicleAdService.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/VehicleAd/edit-form/{id}")
    public String editVehicleAdPage(@PathVariable Long id, Model model) {
        if (this.vehicleAdService.findById(id).isPresent()) {
            VehicleAd vehicleAd = this.vehicleAdService.findById(id).get();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("vehicleAd", vehicleAd);
            model.addAttribute("bodyContent", "VehicleAd");
            return "master-template";

        }
        return "redirect:/ads?error=AdNotFound";
    }
}



