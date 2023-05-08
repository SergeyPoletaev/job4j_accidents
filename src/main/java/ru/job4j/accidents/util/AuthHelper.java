package ru.job4j.accidents.util;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@UtilityClass
public class AuthHelper {
    private static final String ANONYMOUS_USER = "anonymousUser";
    private static final String DEFAULT_USERNAME = "Гость";

    public void addUserNameToModel(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ANONYMOUS_USER.equals(username)) {
            username = DEFAULT_USERNAME;
        }
        model.addAttribute("username", username);
    }
}
