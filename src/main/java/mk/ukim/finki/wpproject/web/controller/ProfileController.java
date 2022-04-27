package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String getProfile(Model model){
        model.addAttribute("bodyContent", "showProfile");
        return "master";
    }
}
