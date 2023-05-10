create table if not exists authorities
(
    id        serial primary key,
    authority varchar(50) unique not null
);

create table if not exists users
(
    id           serial primary key,
    username     varchar(50) unique              not null,
    password     varchar(100)                    not null,
    enabled      boolean default true,
    authority_id int references authorities (id) not null
);

comment on table authorities is 'таблица прав(ролей)';
comment on column authorities.id is 'идентификатор роли';
comment on column authorities.authority is 'роль пользователя в виде ROLE_USER, ROLE_ADMIN и т.д';

comment on table users is 'учетные записи (пользователи системы)';
comment on column users.username is 'имя пользователя';
comment on column users.password is 'пароль в зашифрованном виде';
comment on column users.enabled is 'статус учетной записи (включена или отключена)';
comment on column users.authority_id is 'внешний ключ на роль связанную с пользователем';