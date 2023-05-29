    use user_db;

    create table users
    (
        login varchar(255) primary key,
        password varchar(255),
        name varchar(255)
    )
