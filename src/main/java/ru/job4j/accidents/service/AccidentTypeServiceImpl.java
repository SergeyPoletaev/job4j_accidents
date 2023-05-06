package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentDataTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeServiceImpl implements AccidentTypeService {
    private final AccidentDataTypeRepository repository;

    @Override
    public Collection<AccidentType> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }

}
