package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("user", "Anna");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}
