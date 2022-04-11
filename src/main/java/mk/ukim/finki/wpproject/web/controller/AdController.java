package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public AdController(AdService adService, CategoryService categoryService, CommentService commentService) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.commentService = commentService;
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

}
