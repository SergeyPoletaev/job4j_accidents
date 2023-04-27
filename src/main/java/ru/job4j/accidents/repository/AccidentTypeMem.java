package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentTypeMem implements AccidentTypeRepository {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        types.put(1, new AccidentType(1, "Две машины"));
        types.put(2, new AccidentType(2, "Машина и пешеход"));
        types.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }


    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

}
