package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@RequestMapping("/accident")
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/accidents")
    public String viewAccidents(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accident/accidents";
    }

    @GetMapping("/create")
    public String viewCreate(Model model) {
        return "/accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/accident/accidents";
    }

    @PostMapping("/select")
    public String select(@ModelAttribute Accident accident, RedirectAttributes attr) {
        attr.addAttribute("id", accident.getId());
        return "redirect:/accident/select/{id}";
    }

    @GetMapping("/select/{id}")
    public String viewAccident(@PathVariable int id, Model model, RedirectAttributes attr) {
        Optional<Accident> accidentOpt = accidentService.findById(id);
        if (accidentOpt.isPresent()) {
            model.addAttribute("accident", accidentOpt.get());
            return "/accident/accident";
        }
        attr.addFlashAttribute("message", "Заявление не найдено");
        return "redirect:/accident/error";
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(@PathVariable int id, Model model, RedirectAttributes attr) {
        Optional<Accident> accidentOpt = accidentService.findById(id);
        if (accidentOpt.isPresent()) {
            model.addAttribute("accident", accidentOpt.get());
            return "/accident/update";
        }
        attr.addFlashAttribute("message", "Заявление не найдено");
        return "redirect:/accident/error";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, RedirectAttributes attr) {
        if (accidentService.update(accident)) {
            return "redirect:/accident/accidents";
        }
        attr.addFlashAttribute("message", "При обновлении данных произошла ошибка");
        return "redirect:/accident/error";
    }

    @GetMapping("/error")
    public String error() {
        return "/accident/error";
    }
}