CREATE TABLE boards
(
    board_id INT IDENTITY (1, 1) PRIMARY KEY,
    x_size INT,
    y_size INT
)

CREATE TABLE games
(
    game_id  INT IDENTITY(1, 1) PRIMARY KEY,
    board_id INT REFERENCES boards
)

CREATE TABLE cards
(
    id INT IDENTITY(1, 1) PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(250),
    card_type VARCHAR(50)
)

CREATE TABLE item_cards
(
    id INT PRIMARY KEY REFERENCES cards,
    strength INT,
    health INT
)

CREATE TABLE enemy_cards
(
    id INT PRIMARY KEY REFERENCES cards,
    initial_health INT,
    initial_strength INT
)

CREATE TABLE fields
(
    field_id INT IDENTITY (1, 1) PRIMARY KEY,
    type VARCHAR(50),
    x_position INT,
    y_position INT,
    board_id INT REFERENCES boards,
    enemy_id INT REFERENCES cards
)

CREATE TABLE games_cards
(
    game_id INT REFERENCES games,
    card_id INT REFERENCES cards
)

CREATE TABLE characters
(
    character_id INT IDENTITY (1, 1) PRIMARY KEY,
    name VARCHAR(200),
    profession VARCHAR(200),
    story VARCHAR(1000),
    initial_strength INT,
    initial_health INT,
    field_id INT REFERENCES fields
)

CREATE TABLE games_characters
(
    game_id INT REFERENCES games,
    character_id INT REFERENCES characters
)