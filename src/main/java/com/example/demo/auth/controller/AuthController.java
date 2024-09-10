package com.example.demo.auth.controller;

import com.example.demo.exception.NoteAppException;
import com.example.demo.user.UserRequest;
import com.example.demo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    public static final String MODEL_ATTRIBUTE_USER = "user";
    public static final String MODEL_ATTRIBUTE_ERROR = "error";
    public static final String SESSION_ATTRIBUTE_ERROR = "error";

    private final UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        String errorMessage = (String) request.getSession().getAttribute(SESSION_ATTRIBUTE_ERROR);
        request.getSession().removeAttribute(SESSION_ATTRIBUTE_ERROR);
        model.addAttribute(MODEL_ATTRIBUTE_ERROR, errorMessage);
        model.addAttribute(MODEL_ATTRIBUTE_USER, new UserRequest());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_USER, new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserRequest user, Model model, Locale locale) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            if (e instanceof NoteAppException nae) {
                model.addAttribute(MODEL_ATTRIBUTE_ERROR, nae.getLocalizedMessage(messageSource, locale));
            } else {
                model.addAttribute(MODEL_ATTRIBUTE_ERROR, messageSource.getMessage(e.getMessage(), null, locale));
            }
            model.addAttribute(MODEL_ATTRIBUTE_USER, new UserRequest());
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