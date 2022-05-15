package mk.ukim.finki.wpproject.web.controller;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Comment;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.CommentService;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final AdService adService;
    private final UserService userService;
    private final CommentService commentService;

    public CommentController(AdService adService, UserService userService, CommentService commentService) {
        this.adService = adService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id, @RequestParam Long commentId, HttpSession session) {
        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        Comment comment = commentService.findById(commentId).orElseThrow(RuntimeException::new);
        session.setAttribute("commentContent", comment.getContent());

        ad.getComments().remove(comment);
        adService.save(ad);

        return "redirect:/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long commentId, Model model) {
        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        Comment comment = commentService.findById(commentId).orElseThrow(RuntimeException::new);

        ad.getComments().remove(comment);
        adService.save(ad);

        return "redirect:/{id}";
    }

    @PostMapping("/save/{id}")
    public String saveCommentForAd(@PathVariable Long id, @RequestParam String content, Authentication authentication) {

        Ad ad = adService.findById(id).orElseThrow(() -> new AdNotFoundException(id));

        User user = userService.getUserFromType(authentication.getPrincipal());
        Comment comment = new Comment(content, user);
        commentService.save(comment);

        ad.getComments().add(comment);
        adService.save(ad);

        return "redirect:/{id}";
    }
}
