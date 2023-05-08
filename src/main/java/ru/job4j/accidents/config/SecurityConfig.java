package ru.job4j.accidents.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String FIND_USER_IN_CUSTOM_USERS_TABLE =
            """
                    SELECT username, password, enabled
                    FROM users
                    WHERE username = ?
                    """;
    private static final String FIND_AUTHORITIES_USER_IN_CUSTOM_AUTHORITIES_TABLE =
            """
                    SELECT username, authority
                    FROM authorities
                    WHERE username = ?
                    """;

    private final DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(FIND_USER_IN_CUSTOM_USERS_TABLE)
                .authoritiesByUsernameQuery(FIND_AUTHORITIES_USER_IN_CUSTOM_AUTHORITIES_TABLE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/login")
                .permitAll()
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
