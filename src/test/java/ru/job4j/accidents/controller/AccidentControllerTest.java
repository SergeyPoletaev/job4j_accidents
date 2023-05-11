package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.accidents.App;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void whenGetViewAccidentsThenReturnView() throws Exception {
        mockMvc.perform(get("/accident/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/accidents"));
    }

    @Test
    @WithMockUser
    void whenGetViewCreateThenReturnView() throws Exception {
        mockMvc.perform(get("/accident/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/create"));
    }

    @Test
    @WithMockUser
    void whenGetViewAccidentAndAccidentExistThenReturnView() throws Exception {
        AccidentService accidentService = mock(AccidentService.class);
        when(accidentService.findById(1)).thenReturn(Optional.of(new Accident()));
        mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        accidentService,
                        mock(AccidentTypeService.class),
                        mock(RuleService.class)))
                .build();
        mockMvc.perform(get("/accident/select/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/accident"));
    }

    @Test
    @WithMockUser
    void whenGetViewAccidentsAndAccidentNotExistThenRedirect() throws Exception {
        AccidentService accidentService = mock(AccidentService.class);
        when(accidentService.findById(1)).thenReturn(Optional.empty());
        mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        accidentService,
                        mock(AccidentTypeService.class),
                        mock(RuleService.class)))
                .build();
        mockMvc.perform(get("/accident/select/{id}", 1))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }

    @Test
    @WithMockUser
    void whenGetViewUpdateAndAccidentExistThenReturnView() throws Exception {
        AccidentService accidentService = mock(AccidentService.class);
        when(accidentService.findById(1)).thenReturn(Optional.of(new Accident()));
        mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        accidentService,
                        mock(AccidentTypeService.class),
                        mock(RuleService.class)))
                .build();
        mockMvc.perform(get("/accident/update").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/edit"));
    }

    @Test
    @WithMockUser
    void whenGetViewUpdateAndAccidentNotExistThenRedirect() throws Exception {
        AccidentService accidentService = mock(AccidentService.class);
        when(accidentService.findById(1)).thenReturn(Optional.empty());
        mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        accidentService,
                        mock(AccidentTypeService.class),
                        mock(RuleService.class)))
                .build();
        mockMvc.perform(get("/accident/update").param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errors/error"));
    }
}