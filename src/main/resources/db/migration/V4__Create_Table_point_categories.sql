CREATE TABLE point_categories
(
    point_id      BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (point_id, category_id),
    FOREIGN KEY (point_id) REFERENCES points_of_interest (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);