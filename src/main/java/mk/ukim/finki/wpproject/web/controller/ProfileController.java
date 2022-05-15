package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.security.oauth2.CustomerOAuth2User;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(Model model, Authentication authentication, HttpSession session) {
        User user = userService.getUserFromType(authentication.getPrincipal());

        model.addAttribute("user", user);
        session.setAttribute("userId", user.getId());
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
        Long userId = userService.findByUsername(username).orElseThrow(RuntimeException::new).getId();

        this.userService.edit(userId, username, name, surname, email, phoneNumber);
        return "redirect:/profile";

    }
}
