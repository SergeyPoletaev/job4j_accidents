package ru.job4j.accidents.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.job4j.accidents.config.JdbcConfig;
import ru.job4j.accidents.config.JdbcTestConfig;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AccidentJdbcTemplateTest {
    private static DataSource pool;
    private static JdbcTemplate jdbc;

    @BeforeAll
    static void init() {
        Properties prop = new JdbcTestConfig().loadTestDbProperties();
        JdbcConfig config = new JdbcConfig();
        pool = config.ds(
                prop.getProperty("jdbc.url"),
                prop.getProperty("jdbc.username"),
                prop.getProperty("jdbc.password"),
                prop.getProperty("jdbc.driver")
        );
        jdbc = config.jdbc(pool);
    }

    @AfterAll
    static void close() throws SQLException {
        ((BasicDataSource) pool).close();
    }

    @BeforeEach
    void cleanDb() {
        jdbc.update("delete from accidents_rules");
        jdbc.update("delete from accidents");
    }

    @Test
    void findAll() {
        AccidentRepository repository = new AccidentJdbcTemplate(jdbc);
        Accident accidentDb = repository.save(new Accident()
                .setName("a")
                .setDescription("b")
                .setAddress("c")
                .setType(new AccidentType().setId(1))
                .setRules(Set.of(new Rule().setId(2))));
        Collection<Accident> rsl = repository.findAll();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rsl.size()).isEqualTo(1);
        Accident rslAccident = rsl.iterator().next();
        softly.assertThat(rslAccident.getId()).isEqualTo(accidentDb.getId());
        softly.assertThat(rslAccident.getName()).isEqualTo("a");
        softly.assertThat(rslAccident.getDescription()).isEqualTo("b");
        softly.assertThat(rslAccident.getAddress()).isEqualTo("c");
        softly.assertThat(rslAccident.getType().getId()).isEqualTo(1);
        softly.assertThat(rslAccident.getRules()).isEqualTo(Set.of(new Rule().setId(2)));
        softly.assertAll();
    }

    @Test
    void findById() {
        AccidentRepository repository = new AccidentJdbcTemplate(jdbc);
        Accident accidentDb = repository.save(new Accident()
                .setName("a")
                .setDescription("b")
                .setAddress("c")
                .setType(new AccidentType().setId(1))
                .setRules(Set.of(new Rule().setId(2))));
        Optional<Accident> rslOpt = repository.findById(accidentDb.getId());
        assertThat(rslOpt).isPresent();
        Accident rsl = rslOpt.get();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rsl.getId()).isEqualTo(accidentDb.getId());
        softly.assertThat(rsl.getName()).isEqualTo("a");
        softly.assertThat(rsl.getDescription()).isEqualTo("b");
        softly.assertThat(rsl.getAddress()).isEqualTo("c");
        softly.assertThat(rsl.getType().getId()).isEqualTo(1);
        softly.assertThat(rsl.getRules()).isEqualTo(Set.of(new Rule().setId(2)));
        softly.assertAll();
    }

    @Test
    void save() {
        AccidentRepository repository = new AccidentJdbcTemplate(jdbc);
        Accident accidentDb = repository.save(new Accident()
                .setName("a")
                .setDescription("b")
                .setAddress("c")
                .setType(new AccidentType().setId(1))
                .setRules(Set.of(new Rule().setId(2))));
        Optional<Accident> rslOpt = repository.findById(accidentDb.getId());
        assertThat(rslOpt).isPresent();
        assertThat(rslOpt.get().getId()).isEqualTo(accidentDb.getId());
    }

    @Test
    void update() {
        AccidentRepository repository = new AccidentJdbcTemplate(jdbc);
        Accident accidentDb = repository.save(new Accident()
                .setName("a")
                .setDescription("b")
                .setAddress("c")
                .setType(new AccidentType().setId(1))
                .setRules(Set.of(new Rule().setId(2))));
        Accident newAccident = new Accident()
                .setId(accidentDb.getId())
                .setName("d")
                .setDescription("e")
                .setAddress("f")
                .setType(new AccidentType().setId(2))
                .setRules(Set.of(new Rule().setId(2), new Rule().setId(3)));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(repository.update(newAccident)).isTrue();
        Optional<Accident> rslOpt = repository.findById(accidentDb.getId());
        assertThat(rslOpt).isPresent();
        Accident rsl = rslOpt.get();
        softly.assertThat(rsl.getId()).isEqualTo(accidentDb.getId());
        softly.assertThat(rsl.getName()).isEqualTo("d");
        softly.assertThat(rsl.getDescription()).isEqualTo("e");
        softly.assertThat(rsl.getAddress()).isEqualTo("f");
        softly.assertThat(rsl.getType().getId()).isEqualTo(2);
        softly.assertThat(rsl.getRules()).isEqualTo(Set.of(new Rule().setId(2), new Rule().setId(3)));
        softly.assertAll();
    }
}