create table country(
    id serial primary key,
    title text not null unique check(title  ~ '^[A-z][-a-zA-Z\s]+$'),
    active boolean not null,
    description text not null,
    preview text not null
);

create table city(
    id serial primary key,
    title text not null unique check(title  ~ '^[A-z][-a-zA-Z\s]+$'),
    active boolean not null,
    description text not null,
    preview text not null,
    country_id integer,
    constraint fk_city_country foreign key (country_id) references country(id) on update cascade on delete cascade
);

create table airport(
    id serial primary key,
    title text not null unique check(title  ~ '^[A-z][-a-zA-Z\s]+$'),
    preview text not null,
    active boolean not null,
    city_id integer,
    constraint fk_airport_city foreign key (city_id) references city(id) on update cascade on delete cascade
);

create table company(
    id serial primary key,
    title text not null unique check(title  ~ '^[A-z][-a-zA-Z\s]+$'),
    description text not null
);

create table fly_order(
    id serial primary key,
    from_id integer,
    to_id integer,
    from_date timestamp not null,
    to_date timestamp not null,
    price numeric(5, 2) not null check(price > 0.0),
    plane_num text not null,
    company_id integer,
    constraint fk_order_from foreign key (from_id) references airport(id) on update cascade on delete cascade,
    constraint fk_order_to foreign key (to_id) references airport(id) on update cascade on delete cascade,
    constraint fk_order_company foreign key (company_id) references company(id) on update cascade on delete cascade
);

create unique index unique_plane on fly_order(from_date, plane_num);