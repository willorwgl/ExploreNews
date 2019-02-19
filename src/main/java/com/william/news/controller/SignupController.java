package com.william.news.controller;


import com.google.common.base.Preconditions;
import com.william.news.dataaccess.NewsUserService;
import com.william.news.domain.NewsUser;
import com.william.news.domain.SignupForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private NewsUserService newsUserService;

    public SignupController(NewsUserService newsUserService) {
        Preconditions.checkNotNull(newsUserService, "NewsUserService cannot be null");
        this.newsUserService = newsUserService;
    }

    @GetMapping
    public String signup(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return "redirect:/news";
        }
        model.addAttribute("userSignup", new SignupForm());
        return "signup";
    }

    @RequestMapping("/new")
    public String signup(@ModelAttribute("userSignup") SignupForm userSignUp, RedirectAttributes redirectAttributes) {
        if (userSignUp.getUsername() == null) {
            redirectAttributes.addFlashAttribute("usernameError",
                    "Username field must not be empty");
            return "redirect:/signup";
        }
        if (newsUserService.findUserByUsername(userSignUp.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("usernameError", "Username is already in use.");
            return "redirect:/signup";
        }
        if (userSignUp.getPassword() == null) {
            redirectAttributes.addFlashAttribute("passwordError",
                    "Password field must not be empty");
            return "redirect:/signup";
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", userSignUp.getPassword())) {
            redirectAttributes.addFlashAttribute("passwordError",
                    "Password between 8 and 20 characters; " +
                            "must contain at least one lowercase letter, one uppercase letter, one numeric digit, " +
                            "and one special character, but cannot contain whitespace" );
            return "redirect:/signup";
        }
        if (!userSignUp.getPassword().equals(userSignUp.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("confirmPasswordError",
                    "Passwords must match" );
            return "redirect:/signup";
        }
        NewsUser newsUser = new NewsUser(userSignUp.getUsername(), userSignUp.getPassword(), "USER");
        newsUserService.createUser(newsUser);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        newsUser,
                        newsUser.getPassword(),
                        newsUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/news";
    }
}
