package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.util.AuthHelper;

@Controller
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping("/error")
    public String error(Model model) {
        AuthHelper.addUserNameToModel(model);
        return "/errors/error";
    }
}
