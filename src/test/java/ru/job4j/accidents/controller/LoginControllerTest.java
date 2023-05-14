package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    void whenGetLoginParamsFreeThenReturnView() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andDo(print())
                .andExpect(model().attribute("username", "Гость"))
                .andExpect(model().attribute("rslAuth", false))
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));
    }

    @Test
    void whenGetLoginAndLogoutParamIsTrueThenReturnView() throws Exception {
        mockMvc.perform(get("/auth/login")
                        .param("logout", "true"))
                .andDo(print())
                .andExpect(model().attribute("username", "Гость"))
                .andExpect(model().attribute("rslAuth", false))
                .andExpect(model().attribute("message", "Вы успешно вышли из своей учетной записи!!!"))
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));
    }

    @Test
    void whenGetLoginAndErrorParamIsTrueThenReturnView() throws Exception {
        mockMvc.perform(get("/auth/login")
                        .param("error", "true"))
                .andDo(print())
                .andExpect(model().attribute("username", "Гость"))
                .andExpect(model().attribute("rslAuth", true))
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));
    }

    @Test
    @WithMockUser
    void whenGetLogoutThenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get("/auth/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login?logout=true"));
    }
}