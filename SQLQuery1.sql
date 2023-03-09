use xdd;

drop table if exists characters;
drop table if exists players;


create table characters(
id int,
name varchar(6969)
);

create table players(
id int,
name varchar(6969)
);

insert into players values (1, 'Adam');

insert into characters values (1, 'kototot')