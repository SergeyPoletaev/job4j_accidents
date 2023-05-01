package ru.job4j.accidents.repository;

import org.hibernate.Session;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface CrudRepository {

    void run(Consumer<Session> command);

    void run(String query, Map<String, Object> args);

    <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args);

    <T> Collection<T> query(String query, Class<T> cl);

    <T> Collection<T> query(String query, Class<T> cl, Map<String, Object> args);
}
