package ru.job4j.accidents.service.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserDataRepository;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDataRepository userDataRepository;
    @Mock
    private AuthorityService authorityService;

    @Test
    void whenSaveThenReturnOptUserWithIdIsNotZero() {
        UserService userService =
                new UserServiceImpl(userDataRepository, new BCryptPasswordEncoder(), authorityService);
        when(authorityService.findByAuthority(anyString()))
                .thenReturn(Optional.of(new Authority().setAuthority("ROLE_USER")));
        when(userDataRepository.save(any(User.class))).thenReturn(new User().setId(1));
        Optional<User> rslUser = userService.save(new User().setPassword("123"));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDataRepository).save(userArgumentCaptor.capture());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rslUser).isPresent();
        softly.assertThat(rslUser.get().getId()).isEqualTo(1);
        softly.assertThat(userArgumentCaptor.getValue().getPassword().startsWith("$2a$10$")).isTrue();
        softly.assertThat(userArgumentCaptor.getValue().getAuthority().getAuthority()).isEqualTo("ROLE_USER");
        softly.assertAll();
    }

    @Test
    void whenSaveThenReturnOptUserEmpty() {
        UserService userService =
                new UserServiceImpl(userDataRepository, new BCryptPasswordEncoder(), authorityService);
        when(authorityService.findByAuthority(anyString()))
                .thenReturn(Optional.empty());
        Optional<User> rslUser = userService.save(new User().setPassword("123"));
        verify(userDataRepository, times(0)).save(any(User.class));
        assertThat(rslUser).isEmpty();
    }

    @Test
    void whenSaveWithExThenReturnOptUserEmpty() {
        UserService userService =
                new UserServiceImpl(userDataRepository, new BCryptPasswordEncoder(), authorityService);
        when(authorityService.findByAuthority(anyString()))
                .thenReturn(Optional.of(new Authority().setAuthority("ROLE_USER")));
        doAnswer((invocation) -> {
            throw new Exception();
        }).when(userDataRepository).save(any(User.class));
        Optional<User> rslUser = userService.save(new User().setPassword("123"));
        verify(userDataRepository).save(any(User.class));
        assertThat(rslUser).isEmpty();
    }

}