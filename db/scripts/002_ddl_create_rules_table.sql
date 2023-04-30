create table if not exists rules
(
    id   serial primary key,
    name varchar not null
);

comment on table rules is 'Статьи нарушения';
comment on column rules.id is 'Идентификатор';
comment on column rules.name is 'Наименование статьи нарушения';