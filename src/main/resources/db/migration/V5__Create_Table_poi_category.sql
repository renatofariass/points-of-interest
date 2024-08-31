CREATE TABLE poi_category
(
    poi_id      BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (poi_id, category_id),
    FOREIGN KEY (poi_id) REFERENCES points_of_interest (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);