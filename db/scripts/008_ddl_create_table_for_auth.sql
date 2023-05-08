create table if not exists users
(
    username varchar(50)  not null primary key,
    password varchar(100) not null,
    enabled  boolean      not null
);

create table if not exists authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);

comment on table users is 'учетные записи (пользователи системы)';
comment on column users.username is 'имя пользователя';
comment on column users.password is 'пароль в зашифрованном виде';
comment on column users.enabled is 'статус учетной записи (включена или отключена)';

comment on table authorities is 'таблица связи прав(ролей) и пользователей системы';
comment on column authorities.username is 'имя пользователя';
comment on column authorities.authority is 'роль пользователя в виде ROLE_USER, ROLE_ADMIN и т.д';
