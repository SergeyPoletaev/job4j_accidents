package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void whenGetAddThenReturnView() throws Exception {
        mockMvc.perform(get("/registration/add"))
                .andDo(print())
                .andExpect(model().attribute("username", "Гость"))
                .andExpect(status().isOk())
                .andExpect(view().name("/registration/add"));
    }

    @Test
    void whenPostCreateUniqueUserThenSuccessRslAndRedirect() throws Exception {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(any(User.class))).thenReturn(Optional.of(new User().setId(1)));
        mockMvc.perform(post("/registration/create")
                        .param("username", "a")
                        .param("password", "b"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accident/accidents"));
        verify(userService).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("a");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("b");
    }

    @Test
    void whenPostCreateThenFailVar1AndRedirectWithErrorMsg() throws Exception {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(any(User.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/registration/create")
                        .param("username", "a")
                        .param("password", "b"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Ошибка регистрации!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/add"));
        verify(userService).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("a");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("b");
    }

    @Test
    void whenPostCreateThenFailVar2AndRedirectWithErrorMsg() throws Exception {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(any(User.class))).thenReturn(Optional.of(new User()));
        mockMvc.perform(post("/registration/create")
                        .param("username", "a")
                        .param("password", "b"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Ошибка регистрации!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/add"));
        verify(userService).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("a");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("b");
    }
}