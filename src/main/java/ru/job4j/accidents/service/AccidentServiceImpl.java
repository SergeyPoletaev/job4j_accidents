package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository repository;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @Override
    public Collection<Accident> findAll() {
        return repository.findAll();
    }

    @Override
    public Accident save(Accident accident, HttpServletRequest req) {
        Optional<AccidentType> type = accidentTypeService.findById(accident.getType().getId());
        Set<Rule> rules = getRules(req);
        return type.isPresent() && rules != null
                ? repository.save(accident.setType(type.get()).setRules(rules)) : accident;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean update(Accident accident, HttpServletRequest req) {
        Optional<AccidentType> type = accidentTypeService.findById(accident.getType().getId());
        Set<Rule> rules = getRules(req);
        return type.isPresent()
                && rules != null
                && repository.update(accident.setType(type.get()).setRules(rules));
    }

    private Set<Rule> getRules(HttpServletRequest req) {
        Set<Rule> rules = new HashSet<>();
        for (String id : req.getParameterValues("rIds")) {
            Optional<Rule> ruleOpt = ruleService.findById(Integer.parseInt(id));
            if (ruleOpt.isEmpty()) {
                return null;
            }
            rules.add(ruleOpt.get());
        }
        return rules;
    }
}
