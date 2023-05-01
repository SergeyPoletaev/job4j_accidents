package ru.job4j.accidents.repository.hbm;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.CrudRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class RuleHbm implements RuleRepository {
    private static final String FIND_ALL = "SELECT r FROM Rule r";
    private static final String FIND_BY_ID = "SELECT r FROM Rule r WHERE r.id =:id";

    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query(FIND_ALL, Rule.class);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Rule.class, Map.of("id", id));
    }
}
