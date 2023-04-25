package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(1, new Accident(1, "Авария", "Подрезал", "Ленина д.5"));
        accidents.put(2, new Accident(2, "Нарушение", "Выезд на встречку", "Петровка д.38"));
        accidents.put(3, new Accident(3, "Авария", "Столкновение", "Гагарина д.1"));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
