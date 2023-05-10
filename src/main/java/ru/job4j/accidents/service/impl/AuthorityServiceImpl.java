package ru.job4j.accidents.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityDataRepository;
import ru.job4j.accidents.service.AuthorityService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityDataRepository authorityDataRepository;

    @Override
    public Optional<Authority> findByAuthority(String authority) {
        return authorityDataRepository.findByAuthority(authority);
    }
}
