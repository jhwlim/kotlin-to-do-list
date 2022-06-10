drop table if exists categories;
drop table if exists tasks;

create table categories
(
    id   bigint not null auto_increment,
    name varchar(20),
    primary key (id)
);

create table tasks
(
    id           bigint not null auto_increment,
    name         varchar(100),
    description  longtext,
    status       varchar(20),
    created_dtm  datetime(6),
    modified_dtm datetime(6),
    category_id  bigint,
    primary key (id),
    foreign key (category_id) references categories (id)
);
