CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    full_name         VARCHAR(150) NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    password_hash     VARCHAR(255),
    profile_image_url TEXT,
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW()
);