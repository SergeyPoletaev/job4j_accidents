package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
@Primary
public class AccidentJdbcTemplate implements AccidentRepository {
    private final static String SQL_ACCIDENT_JOIN_ACCIDENT_TYPE_ALL =
            """
                    SELECT a.id, a.name, a.description, a.address, at.id AS atId, at.name AS atName
                    FROM accidents AS a
                    JOIN accident_types AS at ON at.id = a.accident_types_id
                    """;
    private final static String SQL_GET_RULES_FROM_ACCIDENT_RULES_WHERE_ACCIDENT_ID =
            """
                    SELECT r.id, r.name
                    FROM accidents_rules as ar
                    JOIN rules AS r ON ar.rule_id = r.id
                    WHERE ar.accident_id = ?
                    """;
    private final static String SQL_INSERT_ACCIDENT =
            """
                    INSERT INTO accidents (name, description, address, accident_types_id)
                    VALUES (?, ?, ?, ?)
                    """;
    private final static String SQL_INSERT_ACCIDENT_RULES =
            """
                    INSERT INTO accidents_rules (accident_id, rule_id)
                    VALUES (?, ?)
                    """;
    private final static String SQL_ACCIDENT_JOIN_ACCIDENT_TYPE_WHERE_ACCIDENT_ID =
            """
                    SELECT a.id, a.name, a.description, a.address, at.id AS atId, at.name AS atName
                    FROM accidents AS a
                    JOIN accident_types at ON at.id = a.accident_types_id
                    WHERE a.id = ?
                    """;
    private final static String SQL_UPDATE_ACCIDENTS_TABLE_WHERE_ID =
            """
                    UPDATE accidents
                    SET name = ?, description = ?, address = ?, accident_types_id = ?
                    WHERE id = ?
                    """;
    private final static String SQL_DELETE_FROM_ACCIDENT_RULES_WHERE_ACCIDENT_ID =
            """
                    DELETE FROM accidents_rules
                    WHERE accident_id = ?
                    """;

    private final JdbcTemplate jdbc;

    @Override
    @Transactional
    public Collection<Accident> findAll() {
        return jdbc.query(SQL_ACCIDENT_JOIN_ACCIDENT_TYPE_ALL, getAccidentRowMapper());
    }

    @Override
    @Transactional
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(SQL_ACCIDENT_JOIN_ACCIDENT_TYPE_WHERE_ACCIDENT_ID, getAccidentRowMapper(), id));
    }

    @Override
    @Transactional
    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ACCIDENT, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getDescription());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId(Objects.requireNonNull(keyHolder.getKey()).intValue())
                .getRules()
                .forEach(rule -> linkRuleForAccident(accident, rule));
        return accident;
    }

    @Override
    @Transactional
    public boolean update(Accident accident) {
        int r1 = jdbc.update(SQL_UPDATE_ACCIDENTS_TABLE_WHERE_ID,
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        int r2 = jdbc.update(SQL_DELETE_FROM_ACCIDENT_RULES_WHERE_ACCIDENT_ID, accident.getId());
        AtomicInteger count = new AtomicInteger();
        accident.getRules().forEach(rule -> count.addAndGet(linkRuleForAccident(accident, rule)));
        return r1 > 0 && r2 > 0 && count.get() == accident.getRules().size();
    }

    private int linkRuleForAccident(Accident accident, Rule rule) {
        return jdbc.update(SQL_INSERT_ACCIDENT_RULES, accident.getId(), rule.getId());
    }

    private RowMapper<Accident> getAccidentRowMapper() {
        return (resultSet, rowNum) -> new Accident()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setAddress(resultSet.getString("address"))
                .setType(new AccidentType()
                        .setId(resultSet.getInt("atId"))
                        .setName(resultSet.getString("atName")))
                .setRules(getRulesForAccident(resultSet.getInt("id")));
    }

    private Set<Rule> getRulesForAccident(int accidentId) {
        return new HashSet<>(jdbc.query(SQL_GET_RULES_FROM_ACCIDENT_RULES_WHERE_ACCIDENT_ID, getRuleRowMapper(), accidentId));
    }

    private RowMapper<Rule> getRuleRowMapper() {
        return (resultSet, rowNum) -> new Rule()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"));
    }
}
