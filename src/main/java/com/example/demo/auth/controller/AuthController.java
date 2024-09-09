package com.example.demo.auth.controller;

import com.example.demo.user.UserRequest;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserRequest());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserRequest user, Model model) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", new UserRequest());
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping("/")
    public String defaultPath(Principal principal) {
        if (principal != null) {
            return "redirect:/note/list";
        }
        return "redirect:/login";
    }

}