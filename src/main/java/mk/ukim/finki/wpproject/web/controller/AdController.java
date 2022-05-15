package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.*;

@Controller
@RequestMapping(value = {"/", "/ads"})
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CityService cityService;

    public AdController(AdService adService, CategoryService categoryService, UserService userService, CityService cityService) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getAdsPage(@RequestParam(required = false) String error,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam(required = false) Long categoryId,
                             HttpServletRequest request,
                             Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Category> categories = this.categoryService.findAll();
        List<City> cities = this.cityService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("cities", cities);
        model.addAttribute("categoryId", categoryId);

        List<Ad> filteredAds = (List<Ad>) request.getSession().getAttribute("filteredAds");
        if (filteredAds == null)
            filteredAds = adService.findAll();

//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Page<Ad> adPage = this.adService.findPaginated(PageRequest.of(currentPage - 1, pageSize), filteredAds);
//
//        model.addAttribute("adPage", adPage);
//        model.addAttribute("adsSize", adPage.getTotalElements());
//
//        int totalPages = adPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }

        paginationTemplate(page, size, model, filteredAds);

        if (categoryId != null && !this.adService.redirectAdBasedOnCategory(categoryId).equals("OtherAd")) {
            model.addAttribute("filterContent", "fragments/filters/" + this.adService.redirectAdBasedOnCategory(categoryId) + "Filter");
            model.addAttribute("categoryName", this.adService.redirectAdBasedOnCategory(categoryId));
        }
        else {
            model.addAttribute("filterContent", null);
            model.addAttribute("categoryName", "OtherAd");
        }

        model.addAttribute("bodyContent", "mainPage");
        return "master";
    }

    @GetMapping("/invalidateFilters")
    public String invalidateFilters(HttpServletRequest request) {
        request.getSession().setAttribute("filteredAds", null);
        return "redirect:/ads";
    }

    @GetMapping("/{id}")
    public String getAd(@PathVariable Long id) {
        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        return "redirect:/" + this.adService.redirectAdBasedOnCategory(ad.getCategory().getId()) + "/" + ad.getId();
    }

//    @GetMapping("/delete/{id}")
//    public String deleteAd(Model model,@PathVariable Long id){
//        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));
//
//        return "redirect:/" + this.adService.renderAdBasedOnCategory(ad, id, model) + "/delete/" + ad.getId().toString();
//    }

//    @DeleteMapping("/delete/{categoryId}/{id}")
//    public String deleteApartmentAd(@PathVariable Long categoryId, @PathVariable Long id) {
//
//
//
//        return "redirect:/ads";
//    }

    @DeleteMapping("/delete/{id}")
    public String deleteAd(@PathVariable Long id, Authentication authentication) {
        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        User user = userService.getUserFromType(authentication.getPrincipal());

        ad.getSavedByUsers().stream().forEach(u -> u.getSavedAds().remove(ad));

        user.getAdvertisedAds().remove(ad);
        userService.save(user).orElseThrow(RuntimeException::new);

        return "redirect:/myAds";
    }

    @GetMapping("/edit/{id}")
    public String editAd(Model model, @PathVariable Long id) {
        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        return "redirect:/" + this.adService.redirectAdBasedOnCategory(ad.getCategory().getId()) + "/edit-form/" + ad.getId();
    }

    @GetMapping("/savedAds")
    public String getSavedAds(@RequestParam(required = false) String error,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Authentication authentication,
                              Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        User user = userService.getUserFromType(authentication.getPrincipal());

        List<Ad> savedAds = userService.findAllSavedAdsByUser(user.getId());

        paginationTemplate(page, size, model, savedAds);

        model.addAttribute("bodyContent", "showSavedAds");

        return "master";
    }


    @PostMapping("/save/{id}")
    public String saveAdToUser(@PathVariable Long id, Authentication authentication) {
        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        User user = userService.getUserFromType(authentication.getPrincipal());

        if (!user.getSavedAds().contains(ad))
            user.getSavedAds().add(ad);
        userService.save(user).orElseThrow(RuntimeException::new);
        return "redirect:/savedAds";
    }

    @GetMapping("/myAds")
    public String getMyAds(@RequestParam(required = false) String error,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           Authentication authentication,
                           Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        User user = userService.getUserFromType(authentication.getPrincipal());

        List<Ad> ads = userService.findAllAdvertisedAdsByUser(user.getId());

        paginationTemplate(page, size, model, ads);

        model.addAttribute("bodyContent", "showMyAds");

        return "master";

    }

    public void paginationTemplate(Optional<Integer> page, Optional<Integer> size, Model model, List<Ad> ads){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Ad> adPage = this.adService.findPaginated(PageRequest.of(currentPage - 1, pageSize), ads);

        model.addAttribute("adPage", adPage);
        model.addAttribute("adsSize", adPage.getTotalElements());

        int totalPages = adPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

    }

}
