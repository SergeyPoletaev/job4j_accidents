package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger();

    public AccidentMem(AccidentTypeRepository type, RuleRepository rules) {
        save(new Accident(0, "Авария", "Подрезал", "Ленина д.5",
                type.findById(1).orElseThrow(),
                Set.of(rules.findById(1).orElseThrow()))
        );
        save(new Accident(0, "Нарушение", "Выезд на встречку", "Петровка д.38",
                        type.findById(2).orElseThrow(),
                        Set.of(
                                rules.findById(1).orElseThrow(),
                                rules.findById(2).orElseThrow())
                )
        );
        save(new Accident(0, "Авария", "Столкновение", "Гагарина д.1",
                        type.findById(3).orElseThrow(),
                        Set.of(
                                rules.findById(1).orElseThrow(),
                                rules.findById(2).orElseThrow(),
                                rules.findById(3).orElseThrow())
                )
        );
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Accident save(Accident accident) {
        int id = ids.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return accident;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (k, v) -> accident) != null;
    }
}
