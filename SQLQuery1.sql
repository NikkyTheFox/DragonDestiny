use xdd;

drop table if exists characters;
drop table if exists players;


create table characters(
id int identity(1,1),
name varchar(6969)
);

create table players(
id int identity(1,1),
name varchar(6969)
);

insert into players values ('Adam');

insert into characters values ('kototot')
