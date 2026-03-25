drop table if exists users cascade;
create table users (
                       id bigserial primary key,
                       username varchar(30) not null unique,
                       password varchar(80) not null,
                       email varchar(50) unique
);

drop table if exists roles cascade;
create table roles (
                       id serial primary key,
                       name varchar(50) not null
);

drop table if exists user_roles;
create table user_roles (
                            user_id bigint not null,
                            role_id int not null,
                            primary key (user_id, role_id),
                            foreign key (user_id) references users(id),
                            foreign key (role_id) references roles(id)
);

drop table if exists sim_cards;
create table sim_cards (
                           id bigserial primary key,
                           iccid varchar(64) not null unique,
                           operator varchar(50),
                           imei varchar(32)
);
