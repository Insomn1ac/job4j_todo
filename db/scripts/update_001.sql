create table if not exists items(
    id serial primary key,
    name varchar,
    description varchar,
    created varchar,
    done boolean
);