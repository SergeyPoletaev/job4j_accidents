package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository repository;

    @Override
    public Collection<Accident> findAll() {
        return repository.findAll();
    }

    @Override
    public Accident save(Accident accident) {
        return repository.save(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean update(Accident accident) {
        return repository.update(accident);
    }
}
