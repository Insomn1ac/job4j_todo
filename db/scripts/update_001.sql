create table if not exists items(
    id serial primary key,
    name varchar,
    description varchar,
    created varchar,
    done boolean
);

create table if not exists account(
    id serial primary key,
    name varchar,
    login varchar not null unique,
    password varchar not null
);