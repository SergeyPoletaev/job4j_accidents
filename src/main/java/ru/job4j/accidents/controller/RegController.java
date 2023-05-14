package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegController {
    private final UserService userService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("username", "Гость");
        return "/registration/add";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user, RedirectAttributes attr) {
        Optional<User> rsl = userService.save(user);
        if (rsl.isPresent() && rsl.get().getId() != 0) {
            return "redirect:/accident/accidents";
        }
        attr.addFlashAttribute("message", "Ошибка регистрации!");
        return "redirect:/registration/add";
    }
}
