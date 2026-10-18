create table person
(
    id            uuid primary key,
    version       uuid,
    first_name    varchar(200) not null,
    last_name     varchar(200) not null,
    birthday      date         not null,
    created_id    uuid,
    created_date  timestamp(6) with time zone,
    modified_id   uuid,
    modified_date timestamp(6) with time zone
);

create table address
(
    id            uuid primary key,
    version       uuid,
    street        varchar(200) not null,
    number        varchar(200) not null,
    city          varchar(200) not null,
    postal_code   varchar(200) not null,
    created_id    uuid,
    created_date  timestamp(6) with time zone,
    modified_id   uuid,
    modified_date timestamp(6) with time zone,
    person_id     uuid         not null references person (id) on delete cascade,
    position      integer
);

create index ix_address_person_id on address (person_id);
