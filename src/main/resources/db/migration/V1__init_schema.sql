-- SEQUENCES
CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE category_seq_gen START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE spending_id_generator START WITH 1 INCREMENT BY 1;

-- USERS
CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
                       name VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);


-- CATEGORIES
CREATE TABLE category (
                          category_id BIGINT PRIMARY KEY DEFAULT nextval('category_seq_gen'),
                          name VARCHAR(255)
);

-- USER_CATEGORY (Join Table with Composite Key)
CREATE TABLE user_category (
                               user_id BIGINT NOT NULL,
                               category_id BIGINT NOT NULL,
                               index BIGINT,
                               PRIMARY KEY (user_id, category_id),
                               CONSTRAINT fk_usercategory_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
                               CONSTRAINT fk_usercategory_category FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE
);

-- SPENDING
CREATE TABLE spending (
                          id BIGINT PRIMARY KEY DEFAULT nextval('spending_id_generator'),
                          category_category_id BIGINT,
                          user_user_id BIGINT,
                          amount NUMERIC(19,2),
                          description TEXT,
                          updated_at TIMESTAMP,
                          spent_at TIMESTAMP NOT NULL,
                          CONSTRAINT fk_spending_user FOREIGN KEY (user_user_id) REFERENCES users (user_id),
                          CONSTRAINT fk_spending_category FOREIGN KEY (category_category_id) REFERENCES category (category_id)
);
