package ru.job4j.accidents.repository.hbm;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class AccidentHbm implements AccidentRepository {
    private static final String FIND_ALL = "SELECT a FROM Accident a";
    private static final String FIND_BY_ID = "SELECT a FROM Accident a WHERE a.id = :id";

    private final CrudRepository crudRepository;

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query(FIND_ALL, Accident.class);
    }

    @Override
    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Accident.class, Map.of("id", id));
    }

    @Override
    public boolean update(Accident accident) {
        crudRepository.run(session -> session.update(accident));
        return true;
    }
}
