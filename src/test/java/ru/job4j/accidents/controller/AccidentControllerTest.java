package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private AccidentService accidentService;
    @MockBean
    private AccidentTypeService accidentTypeService;
    @MockBean
    private RuleService ruleService;

    @Test
    @WithMockUser
    void whenGetViewAccidentsThenReturnView() throws Exception {
        List<Accident> accidents = List.of(new Accident().setType(new AccidentType()));
        when(accidentService.findAll()).thenReturn(accidents);
        mockMvc.perform(get("/accident/accidents"))
                .andDo(print())
                .andExpect(model().attribute("username", "user"))
                .andExpect(model().attribute("accidents", accidents))
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/accidents"));
    }

    @Test
    @WithMockUser
    void whenGetViewCreateThenReturnView() throws Exception {
        List<AccidentType> types = List.of(new AccidentType());
        Set<Rule> rules = Set.of(new Rule());
        when(accidentTypeService.findAll()).thenReturn(types);
        when(ruleService.findAll()).thenReturn(rules);
        mockMvc.perform(get("/accident/create"))
                .andDo(print())
                .andExpect(model().attribute("username", "user"))
                .andExpect(model().attribute("types", types))
                .andExpect(model().attribute("rules", rules))
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/create"));
    }

    @Test
    @WithMockUser
    void whenGetViewAccidentAndAccidentExistThenReturnView() throws Exception {
        Optional<Accident> optAccident = Optional.of(
                new Accident()
                        .setType(new AccidentType())
                        .setRules(Set.of(new Rule().setName("b"), new Rule().setName("a")))
        );
        when(accidentService.findById(anyInt())).thenReturn(optAccident);
        mockMvc.perform(get("/accident/select/{id}", anyInt()))
                .andDo(print())
                .andExpect(model().attribute("username", "user"))
                .andExpect(model().attribute("accident", optAccident.get()))
                .andExpect(model().attribute("rules", List.of("a", "b")))
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/accident"));
    }

    @Test
    @WithMockUser
    void whenGetViewAccidentsAndAccidentNotExistThenRedirect() throws Exception {
        when(accidentService.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/accident/select/{id}", anyInt()))
                .andDo(print())
                .andExpect(flash().attribute("message", "Заявление не найдено"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }

    @Test
    @WithMockUser
    void whenGetViewUpdateAndAccidentExistThenReturnView() throws Exception {
        Optional<Accident> optAccident = Optional.of(new Accident());
        List<AccidentType> types = List.of(new AccidentType());
        Set<Rule> rules = Set.of(new Rule());
        when(accidentService.findById(anyInt())).thenReturn(optAccident);
        when(accidentTypeService.findAll()).thenReturn(types);
        when(ruleService.findAll()).thenReturn(rules);
        mockMvc.perform(get("/accident/update").param("id", String.valueOf(anyInt())))
                .andDo(print())
                .andExpect(model().attribute("username", "user"))
                .andExpect(model().attribute("types", types))
                .andExpect(model().attribute("rules", rules))
                .andExpect(model().attribute("accident", optAccident.get()))
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/edit"));
    }

    @Test
    @WithMockUser
    void whenGetViewUpdateAndAccidentNotExistThenRedirect() throws Exception {
        when(accidentService.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/accident/update").param("id", String.valueOf(anyInt())))
                .andDo(print())
                .andExpect(flash().attribute("message", "Заявление не найдено"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }

    @Test
    @WithMockUser
    void whenPostSaveAndRulesNotSelectThenRedirectErrorPage() throws Exception {
        mockMvc.perform(post("/accident/save"))
                .andDo(print())
                .andExpect(flash().attribute("message",
                        "Заявление не сохранено. Необходимо выбрать хотя бы одну статью для нарушения"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }

    @Test
    @WithMockUser
    void whenPostSaveWithCorrectParamThenSuccessRslAndReturnView() throws Exception {
        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        Accident accident = new Accident().setId(1);
        when(accidentService.save(any(Accident.class), any(HttpServletRequest.class))).thenReturn(accident);
        mockMvc.perform(post("/accident/save")
                        .param("rIds", "1", "2")
                        .param("name", "a"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accident/accidents"));
        verify(accidentService).save(accidentCaptor.capture(), reqCaptor.capture());
        assertThat(accidentCaptor.getValue().getName()).isEqualTo("a");
    }

    @Test
    @WithMockUser
    void whenPostSaveWithCorrectParamAndNotSavedThenRedirectErrorPage() throws Exception {
        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        when(accidentService.save(any(Accident.class), any(HttpServletRequest.class))).thenReturn(new Accident());
        mockMvc.perform(post("/accident/save")
                        .param("rIds", "1", "2")
                        .param("name", "a"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Заявление не сохранено"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
        verify(accidentService).save(accidentCaptor.capture(), reqCaptor.capture());
    }

    @Test
    @WithMockUser
    void whenPostSelectThenRedirectSelectedAccidentPage() throws Exception {
        int id = 2;
        mockMvc.perform(post("/accident/select")
                        .param("id", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/accident/select/{id}", id));
    }

    @Test
    @WithMockUser
    void whenPostUpdateAndRulesNotSelectThenRedirectErrorPage() throws Exception {
        mockMvc.perform(post("/accident/update"))
                .andDo(print())
                .andExpect(flash().attribute("message",
                        "Заявление не обновлено. Необходимо выбрать хотя бы одну статью для нарушения"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }

    @Test
    @WithMockUser
    void whenPostUpdateWithCorrectParamThenSuccessRslAndReturnView() throws Exception {
        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        when(accidentService.update(any(Accident.class), any(HttpServletRequest.class))).thenReturn(true);
        mockMvc.perform(post("/accident/update")
                        .param("rIds", "1", "2")
                        .param("name", "a"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accident/accidents"));
        verify(accidentService).update(accidentCaptor.capture(), reqCaptor.capture());
        assertThat(accidentCaptor.getValue().getName()).isEqualTo("a");
    }

    @Test
    @WithMockUser
    void whenPostUpdateWithNotCorrectParamAndNotUpdatedThenRedirectErrorPage() throws Exception {
        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        when(accidentService.update(any(Accident.class), any(HttpServletRequest.class))).thenReturn(false);
        mockMvc.perform(post("/accident/update")
                        .param("rIds", "1", "2")
                        .param("name", "a"))
                .andDo(print())
                .andExpect(flash().attribute("message", "При обновлении данных произошла ошибка"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
        verify(accidentService).update(accidentCaptor.capture(), reqCaptor.capture());
        assertThat(accidentCaptor.getValue().getName()).isEqualTo("a");
    }
}