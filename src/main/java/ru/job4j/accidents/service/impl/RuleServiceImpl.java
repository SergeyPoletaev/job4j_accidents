package ru.job4j.accidents.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleDataRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {
    private final RuleDataRepository repository;

    @Override
    public Collection<Rule> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

}
