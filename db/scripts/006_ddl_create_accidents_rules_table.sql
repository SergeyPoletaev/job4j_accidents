create table if not exists accidents_rules
(
    accident_id int not null references accidents (id),
    rule_id     int not null references rules (id),
    PRIMARY KEY (accident_id, rule_id)
);

comment on table rules is 'Таблица связи Инцидентов и статей нарушения';
comment on column accidents_rules.accident_id is 'Внешний ключ на таблицу инцидентов';
comment on column accidents_rules.rule_id is 'Внешний ключ на таблицу статей';