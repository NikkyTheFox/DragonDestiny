    use user_db;

    CREATE TABLE users
    (
        login VARCHAR(255) PRIMARY KEY,
        password VARCHAR(255),
        name VARCHAR(255)
    )
