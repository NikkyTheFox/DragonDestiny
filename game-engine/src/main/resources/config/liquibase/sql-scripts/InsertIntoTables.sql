
-- BOARD
INSERT INTO boards VALUES
    (5, 5);

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
    ('enemy card3', 'some description of enemy 3', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (3, 12, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('enemy card4', 'some description of enemy 4', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (4, 12, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('enemy card5', 'some description of enemy 5', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (5, 12, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('enemy card6', 'some description of enemy 6', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (6, 12, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 1', 'some description of item 1', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (7, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 2', 'some description of item 2', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (8, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 3', 'some description of item 3', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (9, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 4', 'some description of item 4', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (10, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 5', 'some description of item 5', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (11, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('item card 6', 'some description of item 6', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (12, 10, 10);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('eeeee', 'some description of enemy 1', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (13, 12, 10);

INSERT INTO cards (name, description, card_type) VALUES
    ('Old Dragon', 'super main boss dragon', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (14, 12, 10);

-- FIELDS
INSERT INTO fields (type, x_position, y_position, board_id)VALUES
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
    ('TAKE_TWO_CARDS', 0, 2, 1);
INSERT INTO fields (type, x_position, y_position, board_id, enemy_id)VALUES
    ('BRIDGE_FIELD', 0, 1, 1, 14);
INSERT INTO fields (type, x_position, y_position) VALUES
    ('LOSE_ONE_ROUND', 0, 1);
INSERT INTO fields (type, x_position, y_position) VALUES
    ('LOSE_ONE_ROUND', 0, 1);


-- CHARACTERS
INSERT INTO characters VALUES
    ('Harry Potter', 'Wizard', 'There was a wizard called Harry Potter. End of story', 10, 4, 1)

INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Frodo Baggins', 'Hobbit', 'Very cool hobbit. Saved the Middle Earth. End of story', 10, 9, 2)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Winnie the Pooh', 'Bear', 'Nice bear. End of story', 10, 1, 3)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))