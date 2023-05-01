package ru.job4j.accidents.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Properties;

public class HbmTestConfig {

    public SessionFactory getSessionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(HbmTestConfig.class.getClassLoader()
                    .getResourceAsStream("hibernate-test.properties"));
            return new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(Accident.class)
                    .addAnnotatedClass(AccidentType.class)
                    .addAnnotatedClass(Rule.class)
                    .buildSessionFactory();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
