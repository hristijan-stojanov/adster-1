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

import java.util.*;
import java.util.stream.*;

@Controller
@RequestMapping(value = {"/", "/ads"})
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final UserService userService;
    private final CityService cityService;

    public AdController(AdService adService, CategoryService categoryService, CommentService commentService, UserService userService, CityService cityService) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getAdsPage(@RequestParam(required = false) String error, Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String cityId,
                             @RequestParam(required = false) Long categoryId,
                             @RequestParam(required = false) String filteredAds2) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Ad> filteredAds;
        List<Category> categories = this.categoryService.findAll();
        List<City> cities = this.cityService.findAll();

        System.out.println(filteredAds2);
        model.addAttribute("title", title);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("cities", cities);
        model.addAttribute("cityId", cityId);

        if ((title == null || title.isEmpty()) && (cityId == null || cityId.isEmpty()) && (categoryId == null || categoryId.toString().isEmpty()))
            filteredAds = this.adService.findAll();
        else
            filteredAds = this.adService.filter(title, cityId, categoryId);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Ad> adPage = this.adService.findPaginated(PageRequest.of(currentPage-1, pageSize), filteredAds);

        model.addAttribute("adPage", adPage);
        model.addAttribute("adsSize", adPage.getTotalElements());

        int totalPages = adPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("bodyContent", "testAds");

        if (categoryId != null)
            model.addAttribute("filterContent", "fragments/filters/" + this.adService.redirectAdBasedOnCategory(categoryId));
        else
            model.addAttribute("filterContent", null);

        return "master";
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

        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        ad.getSavedByUsers().stream().forEach(u -> u.getSavedAds().remove(ad));

        user.getAdvertisedAds().remove(ad);
        userService.save(user).orElseThrow(RuntimeException::new);

        return "redirect:/myAds";
    }

    @GetMapping("/edit/{id}")
    public String editAd(Model model,@PathVariable Long id){
        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        return "redirect:/" + this.adService.redirectAdBasedOnCategory(ad.getCategory().getId()) + "/edit-form/" + ad.getId();
    }

    @GetMapping("/savedAds")
    public String getSavedAds(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        model.addAttribute("savedAds", userService.findAllSavedAdsByUser(userId));

        model.addAttribute("bodyContent", "showSavedAds");

        return "master";
    }


    @PostMapping("/save/{id}")
    public String saveAdToUser(@PathVariable Long id, Authentication authentication) {
        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.getSavedAds().add(ad);
        userService.save(user).orElseThrow(RuntimeException::new);
        return "redirect:/savedAds";
    }

    @PostMapping("/saveComment/{id}")
    public String saveCommentToAd(@PathVariable Long id, @RequestParam String content, Authentication authentication) {
        System.out.println(content);
        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Comment comment = new Comment(content, user);
        commentService.save(comment);

        ad.getComments().add(comment);
        adService.save(ad);

        return "redirect:/{id}";
    }

    @GetMapping("/myAds")
    public String getMyAds(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        model.addAttribute("myAds", userService.findAllAdvertisedAdsByUser(userId));
        model.addAttribute("bodyContent", "showMyAds");

        return "master";
    }

}
