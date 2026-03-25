insert into roles(name)
values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users(username, password, email)
values
    ('user', '$2a$12$ao8LJnrLFxvjPLZ2tTK6e.YFMOAOoVkW2Rng/xc24pmZgKksFqg1K', 'user@email.com'),
    ('admin', '$2a$12$ao8LJnrLFxvjPLZ2tTK6e.YFMOAOoVkW2Rng/xc24pmZgKksFqg1K', 'admin@email.com');

insert into user_roles(user_id, role_id)
values (1,1),(2,2);
