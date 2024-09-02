CREATE TABLE IF NOT EXISTS points_of_interest
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    x    INT          NOT NULL,
    y    INT          NOT NULL,
    opening_hours VARCHAR(255) NOT NULL,
    closing_hours VARCHAR(255) NOT NULL
);
