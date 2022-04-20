package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.adImage;
import mk.ukim.finki.wpproject.repository.ImageDbRepository;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CommentService;
import mk.ukim.finki.wpproject.service.FileLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileLocationService fileLocationService;
    private final ImageDbRepository imageDbRepository;

    public AdController(AdService adService, CategoryService categoryService, CommentService commentService, FileLocationService fileLocationService, ImageDbRepository imageDbRepository) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.fileLocationService = fileLocationService;
        this.imageDbRepository = imageDbRepository;
    }

    @GetMapping
    public String getAdsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Ad> ads = this.adService.findAll();
        model.addAttribute("ads", ads);
        return "testAds";
    }

    @CrossOrigin(origins = "http://localhost:9091")
    @GetMapping("/imgs")
    public String getImages() {
        return "imgTest";
    }

    @CrossOrigin(origins = "http://localhost:9091")
    @PostMapping("/image")
    public String uploadingImageTest(@RequestParam("file") MultipartFile image, Model model) throws Exception {
        adImage adImage = fileLocationService.save(image.getBytes(), image.getOriginalFilename());

        Ad ad = adService.findById((long)1).get();
        ad.getImages().add(adImage);
        adService.save(ad);

        List<String> imagePaths = fileLocationService.findAllPaths(ad);
        model.addAttribute("images", imageDbRepository.findAll());

        return "showImages";
    }

}
