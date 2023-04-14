use xd;
-- Testing:
drop table if exists characters;
drop table if exists players;
create table characters(
    id int identity(1,1) primary key,
    name varchar(6969)
);
create table players(
    id int identity(1,1) primary key,
    name varchar(6969)
);

-- CARDS:
drop table if exists item_cards;
drop table if exists enemy_cards;
drop table if exists character_cards;

create table item_cards(
    id int identity(1, 1) primary key,
    name varchar(50),
    description varchar(250),
    additional_strength int,
    additional_health int
)
create table enemy_cards(
    id int identity(1, 1) primary key,
    name varchar(50),
    description varchar(250),
    initial_health int,
    initial_strength int
)
create table character_cards(
    id int identity(1, 1) primary key,
    name varchar(50),
    description varchar(250),
    profession varchar(50),
    initial_strength int,
    initial_health int
)


-- BOARDS + FIELDS:
drop table if exists fields;
drop table if exists boards;
GO
create table boards(
    board_id int identity(1, 1) primary key,
    x_size int,
    y_size int
)
GO
create table fields(
    field_id int identity(1, 1) primary key,
    field_type varchar(50) ,
    x_position int,
    y_position int,
    board_id int
)
GO
insert into players values ('Adam');
GO
insert into characters values ('kot');
GO
insert into boards values
(10, 10),
(20, 20);
GO
insert into fields values
('TAKE_ONE_CARD', 1, 0, 1),
('TAKE_ONE_CARD', 1, 0, 1),
('TAKE_ONE_CARD', 1, 0, 1),
('TAKE_ONE_CARD', 1, 0, 1),
('TAKE_TWO_CARDS', 1, 0, 2),
('TAKE_TWO_CARDS', 1, 0, 2);
GO
select * from players;
GO
select * from boards;
GO
select * from fields;

