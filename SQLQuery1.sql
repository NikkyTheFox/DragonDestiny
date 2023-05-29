    use xd;

    -- GAME:
    drop table if exists games;
    create table games
    (
        game_id int identity(1, 1) primary key,
        board_id int
    )

    -- BOARDS + FIELDS:
    drop table if exists fields;
    drop table if exists boards;
    create table boards
    (
        board_id int identity(1, 1) primary key,
        x_size int,
        y_size int
    )
    create table fields
    (
        field_id int identity(1, 1) primary key,
        field_type varchar(50) ,
        x_position int,
        y_position int,
        board_id int
    )
    insert into boards values
    (5, 5);

    insert into fields values -- x, y, character
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

    insert into fields (field_type, x_position, y_position) values
        ('LOSE_ONE_ROUND', 0, 1);
    insert into fields (field_type, x_position, y_position) values
        ('LOSE_ONE_ROUND', 0, 1);

    insert into games values
    (1) -- board id 1

    -- CARDS:
    drop table if exists item_cards;
    drop table if exists enemy_cards;
    drop table if exists cards;
    drop table if exists games_cards;

    create table cards
    (
        id int identity(1, 1) primary key,
        name varchar(50),
        description varchar(250),
        card_type varchar(50)
      --  game_id int
    )
    create table item_cards
    (
        id int primary key references cards,
        additional_strength int,
        additional_health int
    )
    create table enemy_cards
    (
        id int primary key references cards,
        initial_health int,
        initial_strength int
    )

    create table games_cards
    (
        game_id int,
        card_id int
    )

    insert into cards values
        ('enemy card1', 'some description of enemy 1', 'ENEMY_CARD')
    insert into enemy_cards values
        (1, 3, 10);
    insert into games_cards
        SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

    insert into cards values
        ('enemy card2', 'some description of enemy 2', 'ENEMY_CARD')
    insert into enemy_cards values
        (2, 12, 6);
    insert into games_cards
    SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

    insert into cards values
        ('item card 1', 'some description of item 1', 'ITEM_CARD')
    insert into item_cards values
        (3, 10, 10);
    insert into games_cards
    SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

    insert into cards (name, description, card_type) values
        ('eeeee', 'some description of enemy 1', 'ENEMY_CARD')
    insert into enemy_cards values
        (4, 12, 10);

    select * from cards;
    select * from games;
    select * from games_cards;

    -- CHARACTERS:
    drop table if exists characters;
    drop table if exists games_characters;

    create table characters
    (
        character_id int identity(1, 1) primary key,
        name varchar(200),
        profession varchar(200),
        story varchar(1000),
        initial_strength int,
        initial_health int
    )
    create table games_characters
    (
        game_id int,
        character_id int
    )

    insert into characters values
    ('Harry Potter', 'Wizard', 'There was a wizard called Harry Potter. End of story', 10, 4)

    insert into games_characters
    SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

    insert into characters values
    ('Frodo Baggins', 'Hobbit', 'Very cool hobbit. Saved the Middle Earth. End of story', 10, 9)
    insert into games_characters
    SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

    insert into characters values
    ('Winnie the Pooh', 'Bear', 'Nice bear. End of story', 10, 1)
    insert into games_characters
    SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))



