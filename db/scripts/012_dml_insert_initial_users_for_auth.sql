insert into authorities (authority)
values ('ROLE_USER');
insert into authorities (authority)
values ('ROLE_ADMIN');

insert into users (username, password, enabled, authority_id)
values ('user', '$2a$10$aeiqfZV0s/wsym.0kN26M.IV6FEYPDe3.C7oZMUEi897gQzGcTGL2', true, 1);
insert into users (username, password, enabled, authority_id)
values ('admin', '$2a$10$zENoo6RPWoF0DKOWv6n0h.BTH3gck7hGXweUr8QladGcttice7VkW', true, 2);
