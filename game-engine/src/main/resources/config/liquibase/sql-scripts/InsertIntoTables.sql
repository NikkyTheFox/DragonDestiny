
-- BOARD
INSERT INTO boards VALUES
    (5, 5);

-- FIELDS
INSERT INTO fields VALUES
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
INSERT INTO fields (type, x_position, y_position) VALUES
    ('LOSE_ONE_ROUND', 0, 1);
INSERT INTO fields (type, x_position, y_position) VALUES
    ('LOSE_ONE_ROUND', 0, 1);

-- GAME
INSERT INTO games VALUES
    (1)

-- CARDS
INSERT INTO cards VALUES
    ('enemy card1', 'some description of enemy 1', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (1, 3, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('enemy card2', 'some description of enemy 2', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (2, 12, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 1', 'some description of item 1', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (3, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards (name, description, card_type) VALUES
    ('eeeee', 'some description of enemy 1', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (4, 12, 10);

-- CHARACTERS
INSERT INTO characters VALUES
    ('Harry Potter', 'Wizard', 'There was a wizard called Harry Potter. End of story', 10, 4)

INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Frodo Baggins', 'Hobbit', 'Very cool hobbit. Saved the Middle Earth. End of story', 10, 9)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Winnie the Pooh', 'Bear', 'Nice bear. End of story', 10, 1)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))