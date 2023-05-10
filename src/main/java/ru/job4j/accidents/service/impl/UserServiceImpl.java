package ru.job4j.accidents.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.enums.AuthorityTypes;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserDataRepository;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    @Override
    public Optional<User> save(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        Optional<Authority> optDefaultAuthority = authorityService.findByAuthority(AuthorityTypes.USER.getValue());
        Optional<User> rsl = Optional.empty();
        try {
            if (optDefaultAuthority.isPresent()) {
                rsl = Optional.of(userDataRepository
                        .save(user.setPassword(encodePassword).setAuthority(optDefaultAuthority.get())));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return rsl;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDataRepository.findByUsername(username);
    }
}
