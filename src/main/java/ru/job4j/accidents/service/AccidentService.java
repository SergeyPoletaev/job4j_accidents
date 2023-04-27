package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Collection<Accident> findAll();

    Accident save(Accident accident, HttpServletRequest req);

    Optional<Accident> findById(int id);

    boolean update(Accident accident, HttpServletRequest req);
}
