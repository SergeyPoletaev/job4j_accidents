package ru.job4j.accidents.repository.hbm;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class AccidentTypeHbm implements AccidentTypeRepository {
    private static final String FIND_ALL = "SELECT at FROM AccidentType at";
    private static final String FIND_BY_ID = "SELECT at FROM AccidentType at WHERE at.id = :id";

    private final CrudRepository crudRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query(FIND_ALL, AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, AccidentType.class, Map.of("id", id));
    }
}
