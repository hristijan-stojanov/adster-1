package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.adImage;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.ImageDbRepository;
import mk.ukim.finki.wpproject.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.*;

@Controller
@RequestMapping(value = {"/", "/ads"})
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileLocationService fileLocationService;
    private final ImageDbRepository imageDbRepository;
    private final UserService userService;

    public AdController(AdService adService, CategoryService categoryService, CommentService commentService, FileLocationService fileLocationService, ImageDbRepository imageDbRepository, UserService userService) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.fileLocationService = fileLocationService;
        this.imageDbRepository = imageDbRepository;
        this.userService = userService;
    }

    @GetMapping
    public String getAdsPage(@RequestParam(required = false) String error, Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Ad> adPage = this.adService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

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
        return "master";
    }

    @GetMapping("/{id}")
    public String getAd(Model model, @PathVariable Long id) {
        Ad ad = this.adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        return "redirect:/" + this.adService.renderAdBasedOnCategory(ad, id, model) + "/" + ad.getId();
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


    @GetMapping("/myAds")
    public String getMyAds(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        model.addAttribute("myAds", userService.findAllAdvertisedAdsByUser(userId));
        model.addAttribute("bodyContent", "showMyAds");

        return "master";
    }

    @CrossOrigin(origins = "http://localhost:9091")
    @GetMapping("/imgs")
    public String getImages() {
        return "imgTest";
    }

    @PostMapping("/image")
    public String uploadingImageTest(@RequestParam("file") MultipartFile image, Model model) throws Exception {
        adImage adImage = fileLocationService.save(image.getBytes(), image.getOriginalFilename());

        Ad ad = adService.findById((long) 1).get();
        ad.getImages().add(adImage);
        adService.save(ad);

        List<String> imagePaths = fileLocationService.findAllPaths(ad);
        model.addAttribute("images", imageDbRepository.findAll());


        model.addAttribute("bodyContent", "showImages");
        return "master";
    }
}

