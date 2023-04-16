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
insert into players values ('Adam');
GO
insert into characters values ('kot');

-- CARDS:
drop table if exists cards;
drop table if exists item_cards;
drop table if exists enemy_cards;
drop table if exists character_cards;

create table cards
(
    id int identity(1, 1) primary key,
    name varchar(50),
    description varchar(250),
    card_type varchar(50)
)
create table item_cards
(
--     id int identity(1, 1) primary key,
--     name varchar(50),
--     description varchar(250),
    card_id int primary key,
    additional_strength int,
    additional_health int
)
create table enemy_cards
(
--     id int identity(1, 1) primary key,
--     name varchar(50),
--     description varchar(250),
    card_id int primary key,
    initial_health int,
    initial_strength int
)


-- nested insert in JAVA
-- DO LATER!!!!!!!!!!!!!!!!!!!! ???????????????????????
insert into cards values
('enemy card1', 'some description of enemy 1', 'ENEMY_CARD')
insert into enemy_cards values
(1, 12, 10);

insert into cards values
('enemy card2', 'some description of enemy 2', 'ENEMY_CARD')
insert into enemy_cards values
(2, 12, 10);

insert into cards values
('item card 1', 'some description of item 1', 'ITEM_CARD')
insert into item_cards values
(3, 10, 10);
go
-- drop view if exists enemy_card_view
-- go
-- create view enemy_card_view as
--     select id, name, description, card_type, initial_health, initial_strength from
--     (   select * from cards as a
--         inner join enemy_cards as b on b.card_id = a.id
--         where a.card_type = 'ENEMY_CARD') x;
-- go
-- select * from enemy_card_view;
--
-- go
-- drop view if exists item_card_view
-- go
-- create view item_card_view as
-- select id, name, description, card_type, additional_strength, additional_health from
--     (   select * from cards as a
--         inner join item_cards as b on b.card_id = a.id
--         where a.card_type = 'ITEM_CARD') x;
-- go


select * from cards;

-- BOARDS + FIELDS:
drop table if exists fields;
drop table if exists boards;
create table boards(
    board_id int identity(1, 1) primary key,
    x_size int,
    y_size int
)
create table fields(
    field_id int identity(1, 1) primary key,
    field_type varchar(50) ,
    x_position int,
    y_position int,
    board_id int
)
insert into boards values
(5, 5);
insert into fields values -- x, y, board
-- y 0
('TAKE_ONE_CARD', 0, 0, 1),
('TAKE_ONE_CARD', 1, 0, 1),
('TAKE_ONE_CARD', 2, 0, 1),
('TAKE_ONE_CARD', 3, 0, 1),
('TAKE_ONE_CARD', 4, 0, 1),
-- x size_x
('TAKE_TWO_CARDS', 4, 1, 1),
('TAKE_TWO_CARDS', 4, 2, 1),
('TAKE_TWO_CARDS', 4, 3, 1),
('TAKE_TWO_CARDS', 4, 4, 1),
-- y size_y
('TAKE_ONE_CARD', 3, 4, 1),
('TAKE_ONE_CARD', 2, 4, 1),
('TAKE_ONE_CARD', 1, 4, 1),
('TAKE_ONE_CARD', 0, 4, 1),
-- x 0
('TAKE_TWO_CARDS', 0, 3, 1),
('TAKE_TWO_CARDS', 0, 2, 1),
('TAKE_TWO_CARDS', 0, 1, 1);


-- GAME:
drop table if exists games;
create table games(
    game_id int identity(1, 1) primary key,
    board_id int
)
insert into games values
(1)

select * from players;
GO
select * from boards;
GO
select * from fields;

