package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/accident")
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/accidents")
    public String viewAccidents(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("accidents", accidentService.findAll());
        return "/accident/accidents";
    }

    @GetMapping("/create")
    public String viewCreate(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, RedirectAttributes attr, HttpServletRequest req) {
        if (req.getParameterValues("rIds") == null) {
            attr.addFlashAttribute("message",
                    "Заявление не сохранено. Необходимо выбрать хотя бы одну статью для нарушения");
            return "redirect:/errors/error";
        }
        if (accidentService.save(accident, req).getId() != 0) {
            return "redirect:/accident/accidents";
        }
        attr.addFlashAttribute("message", "Заявление не сохранено");
        return "redirect:/errors/error";
    }

    @PostMapping("/select")
    public String select(@RequestParam int id, RedirectAttributes attr) {
        attr.addAttribute("id", id);
        return "redirect:/accident/select/{id}";
    }

    @GetMapping("/select/{id}")
    public String viewAccident(@PathVariable int id,
                               Model model,
                               RedirectAttributes attr,
                               @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        Optional<Accident> accidentOpt = accidentService.findById(id);
        if (accidentOpt.isPresent()) {
            model.addAttribute("accident", accidentOpt.get());
            model.addAttribute("rules",
                    accidentOpt.get().getRules().stream()
                            .map(Rule::getName)
                            .sorted()
                            .collect(Collectors.toList())
            );
            return "/accident/accident";
        }
        attr.addFlashAttribute("message", "Заявление не найдено");
        return "redirect:/errors/error";
    }

    @GetMapping("/update")
    public String viewUpdate(@RequestParam("id") int id,
                             Model model,
                             RedirectAttributes attr, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        Optional<Accident> accidentOpt = accidentService.findById(id);
        if (accidentOpt.isPresent()) {
            model.addAttribute("types", accidentTypeService.findAll());
            model.addAttribute("rules", ruleService.findAll());
            model.addAttribute("accident", accidentOpt.get());
            return "/accident/edit";
        }
        attr.addFlashAttribute("message", "Заявление не найдено");
        return "redirect:/errors/error";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, RedirectAttributes attr, HttpServletRequest req) {
        if (req.getParameterValues("rIds") == null) {
            attr.addFlashAttribute("message",
                    "Заявление не обновлено. Необходимо выбрать хотя бы одну статью для нарушения");
            return "redirect:/errors/error";
        }
        if (accidentService.update(accident, req)) {
            return "redirect:/accident/accidents";
        }
        attr.addFlashAttribute("message", "При обновлении данных произошла ошибка");
        return "redirect:/errors/error";
    }

}