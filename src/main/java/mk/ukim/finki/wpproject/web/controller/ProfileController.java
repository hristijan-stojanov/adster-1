package mk.ukim.finki.wpproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/Profile")
public class ProfileController {

    @GetMapping
    public String getProfile(Model model){
        return "showProfile";
    }
}
