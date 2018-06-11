CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NULL,
    enable boolean DEFAULT NULL,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    password VARCHAR(255) DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    user_name VARCHAR(255) DEFAULT NULL
);

CREATE TABLE roles
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) DEFAULT NULL
);

CREATE TABLE users_roles
(
    user_id INTEGER REFERENCES users(id),
    role_id INTEGER REFERENCES roles(id)
);