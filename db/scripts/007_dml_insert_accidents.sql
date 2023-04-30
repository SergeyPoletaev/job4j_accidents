insert into accidents (name, description, address, accident_types_id)
values ('Авария', 'Подрезал', 'Ленина д.5', 1);
insert into accidents (name, description, address, accident_types_id)
values ('Нарушение', 'Выезд на встречку', 'Петровка д.38', 2);
insert into accidents (name, description, address, accident_types_id)
values ('Авария', 'Столкновение', 'Гагарина д.1', 3);

insert into accidents_rules (accident_id, rule_id)
values (1, 1);
insert into accidents_rules (accident_id, rule_id)
values (2, 1);
insert into accidents_rules (accident_id, rule_id)
values (2, 2);
insert into accidents_rules (accident_id, rule_id)
values (3, 1);
insert into accidents_rules (accident_id, rule_id)
values (3, 2);
insert into accidents_rules (accident_id, rule_id)
values (3, 3);