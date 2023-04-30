create table if not exists accidents
(
    id                serial primary key,
    name              varchar                            not null,
    description       varchar                            not null,
    address           varchar                            not null,
    accident_types_id int references accident_types (id) not null
);

comment on table accidents is 'Инциденты';
comment on column accidents.id is 'Идентификатор';
comment on column accidents.name is 'Наименование инцидента';
comment on column accidents.description is 'Подробное описание';
comment on column accidents.address is 'Адрес, где произошел инцидент';
comment on column accidents.accident_types_id is 'Внешний ключ на запись в таблице тип инцидента';