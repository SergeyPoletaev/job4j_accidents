package ru.job4j.accidents.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorityTypes {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

}
