-- Таблиця users
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    user_id      BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    name         VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255)
);

-- Таблиця basic_category
CREATE SEQUENCE IF NOT EXISTS basic_category_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE basic_category
(
    basic_category_id BIGINT PRIMARY KEY DEFAULT nextval('basic_category_id_seq'),
    name              VARCHAR(255)
);

-- Таблиця зв'язку users <-> basic_category (many-to-many)
CREATE TABLE basic_users_categories
(
    user_id           BIGINT NOT NULL,
    basic_category_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, basic_category_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (basic_category_id) REFERENCES basic_category (basic_category_id) ON DELETE CASCADE
);

-- Таблиця custom_category
CREATE SEQUENCE IF NOT EXISTS category_seq_gen START WITH 1 INCREMENT BY 1;

CREATE TABLE custom_category
(
    id           BIGINT PRIMARY KEY DEFAULT nextval('category_seq_gen'),
    name         VARCHAR(255),
    user_user_id BIGINT,
    FOREIGN KEY (user_user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

