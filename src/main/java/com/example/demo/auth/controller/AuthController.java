package com.example.demo.auth.controller;


import com.example.demo.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView showLoginForm(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (error != null) {
            modelAndView.addObject("error", "Invalid username or password.");
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (error != null) {
            modelAndView.addObject("error", error);
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String username,
                                     @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            // Handle registration error (e.g., username already exists)
            return new ModelAndView("redirect:/register?error=" + e.getMessage());
        }
    }
}