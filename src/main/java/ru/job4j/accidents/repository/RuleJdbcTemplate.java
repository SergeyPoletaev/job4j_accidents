package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class RuleJdbcTemplate implements RuleRepository {
    private final static String SQL_FIND_ALL = "select id, name from rules";
    private final static String SQL_FIND_BY_ID = "select id, name from rules where id = ?";

    private final JdbcTemplate jdbc;

    @Override
    @Transactional
    public Collection<Rule> findAll() {
        return this.jdbc.query(SQL_FIND_ALL, getRuleRowMapper());
    }

    @Override
    @Transactional
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SQL_FIND_BY_ID, getRuleRowMapper(), id));
    }

    private RowMapper<Rule> getRuleRowMapper() {
        return (resultSet, rowNum) -> new Rule()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"));
    }
}
