create table if not exists account(
    id serial primary key,
    name varchar,
    login varchar not null unique,
    password varchar not null unique
);