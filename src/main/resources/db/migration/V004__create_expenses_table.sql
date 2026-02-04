CREATE TABLE expenses
(
    expense_id SERIAL PRIMARY KEY,
    group_id   BIGINT REFERENCES groups (id) ON DELETE CASCADE,
    creator_id BIGINT REFERENCES users (id) ON DELETE SET NULL,
    description VARCHAR(255),
    amount      DECIMAL(12, 2) NOT NULL,
    expense_date DATE           NOT NULL DEFAULT CURRENT_DATE,
    created_at  TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE expense_participants
(
    expense_id BIGINT REFERENCES expenses (expense_id) ON DELETE CASCADE,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (expense_id, user_id)
);
