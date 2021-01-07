create table toOne_role (
    id serial primary key,
    name varchar(2000)
);

create table toOne_user (
    id serial primary key,
    name varchar(2000),
    role_id int not null references toOne_role(id)
);