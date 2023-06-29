
-- BOARD
INSERT INTO boards VALUES
    (5, 5);

-- GAME
INSERT INTO games VALUES
    (1)

-- CARDS
INSERT INTO cards VALUES
    ('Wild Bear', 'Wild Bear', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (1, 1, 4);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Crazy Boar', 'Crazy Boar', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (2, 1, 2);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Omnivorous Hydra', 'Omnivorous Hydra', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (3, 1, 5);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Aggressive Parrot', 'Aggressive Parrot', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (4, 1, 3);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Vicious Snake', 'Vicious Snake', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (5, 1, 6);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Furious Werewolf', 'Furious Werewolf', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (6, 1, 7);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Abominable Snowman', 'Abominable Snowman', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (7, 1, 7);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Ginny', 'Ginny is a Bridge Guardian, self independent female lizard. She protects the Dragostar''s lair. ', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (8, 1, 15);

INSERT INTO cards (name, description, card_type) VALUES
    ('Dragostar', 'Dragostar was a dear friend and a cook of previous big dragon boss - Puterox. He then suddenly decided to poison his master and kill him. Now he rules the Mountain Of Doom and owns the treasure.', 'ENEMY_CARD')
INSERT INTO enemy_cards VALUES
    (9, 5, 20);

INSERT INTO cards VALUES
    ('Dwarven Axe', 'Dwarven Axe', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (10, 2, 0);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Mighty Hammer', 'Mighty Hammer', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (11, 3, 0);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Wooden Lute', 'Wooden Lute', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (12, 1, 1);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Magic Silver Mask', 'Magic Silver Mask', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (13, 0, 2);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Elven Shield', 'Elven Shield', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (14, 0, 2);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Shining Sword', 'Shining Sword', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (15, 2, 0);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

INSERT INTO cards VALUES
    ('Last Offer Wheelchair', 'Last Offer Wheelchair', 'ITEM_CARD')
INSERT INTO item_cards VALUES
    (16, 0, 4);
INSERT INTO games_cards
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('cards'))

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
INSERT INTO fields (type, x_position, y_position, board_id, enemy_id) VALUES
    ('BRIDGE_FIELD', 0, 1, 1, 8);
INSERT INTO fields (type, x_position, y_position, board_id, enemy_id) VALUES
     ('BOSS_FIELD', -1, -1, 1, 9);

INSERT INTO characters VALUES
    ('Dahamut', 'Dragon', 'Dahamut was born in a loving family. His parents were killed by his uncle when he was 2 months old. Since then, he craves for revenge.', 4, 8, 1)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Carixus', 'Dragon', 'Carixus is an orphan dragon found and raised by wolves. He is very compassionate and does not like to fight.', 6, 7, 2)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Fergusso', 'Dragon', 'Fergusso is a crazy vegetarian dragon that decided not to eat meat anymore after seeing a little puppy. He is though very aggressive for his territory.', 8, 5, 3)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Amazonija', 'Dragon', 'Amazonija comes from a long family of fighters. She was trained to fight since she was a newborn child.', 7, 6, 4)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Cinder', 'Dragon', 'Cinder had a very damageable childhood - she was thrown into a volcano as a child. Her scales and claves adapted to the heat of lava and she become almost indestructible.', 2, 10, 5)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))

INSERT INTO characters VALUES
    ('Florina', 'Dragon', 'Florina is a venom spitting dragon that takes her power from poisonous flowers. She hates killing animals.', 3, 8, 6)
INSERT INTO games_characters
SELECT IDENT_CURRENT('games'), (SELECT IDENT_CURRENT('characters'))