package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {
    private final static String SQL_FIND_ALL = "select id, name from accident_types";
    private final static String SQL_FIND_BY_ID = "select id, name from accident_types where id = ?";

    private final JdbcTemplate jdbc;

    @Override
    @Transactional
    public Collection<AccidentType> findAll() {
        return this.jdbc.query(SQL_FIND_ALL, getAccidentTypeRowMapper());
    }

    @Override
    @Transactional
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SQL_FIND_BY_ID, getAccidentTypeRowMapper(), id));
    }

    private RowMapper<AccidentType> getAccidentTypeRowMapper() {
        return (resultSet, rowNum) -> new AccidentType()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"));
    }
}
