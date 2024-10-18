create table if not exists category
(
    id integer not null primary key,
    description varchar(255),
    name varchar(255)
);

create table if not exists product
(
    id integer not null primary key,
    description varchar(255),
    name varchar(255),
    available_quantity double precision not null,
    price numeric(32,2),
    category_id integer
        constraints fkcat1 refernces category
);

create sequence if not exists category_sequence increment by 50;
create sequence if not exists product_sequence increment by 50;