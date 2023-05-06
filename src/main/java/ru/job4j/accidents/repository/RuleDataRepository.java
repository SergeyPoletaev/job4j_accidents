package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface RuleDataRepository extends CrudRepository<Rule, Integer> {

    @Override
    @NonNull
    Collection<Rule> findAll();
}
