package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

public interface AccidentDataTypeRepository extends CrudRepository<AccidentType, Integer> {

    @Override
    @NonNull
    Collection<AccidentType> findAll();
}
