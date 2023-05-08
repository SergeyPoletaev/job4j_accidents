insert into users (username, password, enabled)
values ('user', '$2a$10$aeiqfZV0s/wsym.0kN26M.IV6FEYPDe3.C7oZMUEi897gQzGcTGL2', true);
insert into users (username, password, enabled)
values ('admin', '$2a$10$zENoo6RPWoF0DKOWv6n0h.BTH3gck7hGXweUr8QladGcttice7VkW', true);

insert into authorities (username, authority)
values ('user', 'ROLE_USER');
insert into authorities (username, authority)
values ('admin', 'ROLE_USER');
insert into authorities (username, authority)
values ('admin', 'ROLE_ADMIN');