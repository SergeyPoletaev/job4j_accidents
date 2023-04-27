package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeRepository {

    Collection<AccidentType> findAll();

    Optional<AccidentType> findById(int id);
}
