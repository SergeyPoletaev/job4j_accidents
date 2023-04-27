package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {

    Collection<Rule> findAll();

    Optional<Rule> findById(int id);
}
