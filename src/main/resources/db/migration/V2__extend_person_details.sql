alter table person
    alter column birthday drop not null;

alter table person
    add column gender      varchar(20),
    add column description varchar(2000),
    add column email       varchar(254),
    add column phone       varchar(50),
    add column type        varchar(20);
