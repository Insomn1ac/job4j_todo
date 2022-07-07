create table if not exists items(
    id serial primary key,
    description varchar,
    created varchar,
    done boolean
);