package mk.ukim.finki.wpproject.web.controller.AddAdControllers;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.BookAd;
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
@RequestMapping("/BookAd")
public class BookAdController {

    private final CategoryService categoryService;
    private final BookAdService bookAdService;
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;

    public BookAdController(CategoryService categoryService, BookAdService bookAdService, CityService cityService, UserService userService, ImageService imageService) {
        this.categoryService = categoryService;
        this.bookAdService = bookAdService;
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showBookAd(@PathVariable Long id, Model model) {

        BookAd bookAd = this.bookAdService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        model.addAttribute("ad", bookAd);
        model.addAttribute("comments", bookAd.getComments());
        model.addAttribute("additionalContent", "showBookAd");

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
            List<Genre> genreList = Arrays.asList(Genre.values());

            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("genreList", genreList);

            model.addAttribute("bodyContent", "addAdsTemplates/addBookAd");
            return "master";
        }
        return "redirect:/add?error=YouHaveNotSelectedCategory";
    }

    @PostMapping("/add")
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
            @RequestParam String author,
            @RequestParam int yearMade,
            @RequestParam int numPages,
            @RequestParam Genre genre,
            @RequestParam("files") List<MultipartFile> images,
            Authentication authentication
    ) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        if (id != null) {
            this.bookAdService.edit(id, title, description, isExchangePossible, isDeliveryPossible,
                    price, cityId, type, condition, categoryId, author, yearMade, numPages, genre);
        } else {
            BookAd bookAd = this.bookAdService.save(title, description, isExchangePossible, isDeliveryPossible,
                            price, cityId, type, condition, categoryId, user.getId(), author, yearMade, numPages, genre)
                    .orElseThrow(RuntimeException::new);

            user.getAdvertisedAds().add(bookAd);
            this.userService.save(user);

            imageService.addImagesToAd(bookAd.getId(), images);
        }
        return "redirect:/ads";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookAd(@PathVariable Long id, Model model) {
        if (this.bookAdService.findById(id).isPresent()) {

            BookAd bookAd = this.bookAdService.findById(id).get();
            Category category = bookAd.getCategory();
            List<City> cityList = this.cityService.findAll();
            List<AdType> adTypeList = Arrays.asList(AdType.values());
            List<Condition> conditionList = Arrays.asList(Condition.values());
            List<Genre> genreList = Arrays.asList(Genre.values());

            model.addAttribute("bookAd", bookAd);
            model.addAttribute("category_1", category);
            model.addAttribute("cityList", cityList);
            model.addAttribute("adTypeList", adTypeList);
            model.addAttribute("conditionList", conditionList);
            model.addAttribute("genreList", genreList);

            model.addAttribute("bodyContent", "addAdsTemplates/addBookAd");

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
                                 @RequestParam(required = false) String author,
                                 @RequestParam(required = false) Genre genre,
                                 HttpServletRequest request) {

        List<Ad> filteredAds = bookAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, author, genre);

        request.getSession().setAttribute("filteredAds", filteredAds);

        if (categoryId != null)
            return "redirect:/ads?categoryId=" + categoryId;
        else
            return "redirect:/ads";
    }
}
