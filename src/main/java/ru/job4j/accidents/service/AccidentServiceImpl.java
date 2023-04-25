package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository repository;

    @Override
    public Collection<Accident> findAll() {
        return repository.findAll();
    }
}
