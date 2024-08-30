CREATE TABLE IF NOT EXISTS points_of_interest
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    x    INT          NOT NULL,
    y    INT          NOT NULL,
    category_id BIGINT REFERENCES categories(id)
);
