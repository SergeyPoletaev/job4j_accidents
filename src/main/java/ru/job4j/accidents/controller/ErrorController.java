package ru.job4j.accidents.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping("/error")
    public String error(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        return "/errors/error";
    }
}
