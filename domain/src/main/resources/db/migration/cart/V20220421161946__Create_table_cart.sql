CREATE TABLE IF NOT EXISTS cart
(
    id              SERIAL PRIMARY KEY,
    total_cost      REAL NOT NULL,
    user_id       INTEGER NOT NULL UNIQUE,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
            ON DELETE CASCADE
);
