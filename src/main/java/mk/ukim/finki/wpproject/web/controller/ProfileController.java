package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(Model model,
                             Authentication authentication){

        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "showProfile");
        return "master";
    }

    @PostMapping("/edit")
    public String editProfile(@RequestParam (required = false) String username,
                              @RequestParam (required = false) String name,
                              @RequestParam (required = false) String surname,
                              @RequestParam (required = false) String email,
                              @RequestParam (required = false) String phoneNumber,
                              Authentication authentication){
        Long userId = ((User) authentication.getPrincipal()).getId();

        this.userService.edit(userId, username, name, surname, email, phoneNumber);
        return "redirect:/profile";

    }
}
