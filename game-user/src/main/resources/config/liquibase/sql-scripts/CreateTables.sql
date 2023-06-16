CREATE TABLE users
(
    login VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(255)
)

CREATE TABLE games
(
    id VARCHAR(255) PRIMARY KEY,
)

CREATE TABLE users_games
(
    user_id VARCHAR(255) REFERENCES users,
    game_id VARCHAR(255) REFERENCES games
)
