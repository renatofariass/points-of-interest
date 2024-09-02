CREATE TABLE IF NOT EXISTS users
(
    id        VARCHAR(255)    PRIMARY KEY NOT NULL,
    username VARCHAR(255)     NOT NULL UNIQUE,
    full_name VARCHAR(255)    NOT NULL,
    password  VARCHAR(255)    NOT NULL,
    role      VARCHAR(255)    NOT NULL
);