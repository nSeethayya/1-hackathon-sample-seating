CREATE TABLE events
(
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT NULL,
    name VARCHAR(255) DEFAULT NULL,
    location VARCHAR(255) DEFAULT NULL,
    state VARCHAR(2) DEFAULT NULL,
    host VARCHAR(255) DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE messages
(
    id SERIAL PRIMARY KEY,
    message VARCHAR(255) DEFAULT NULL,
    user_id VARCHAR(255) DEFAULT NULL,
    event_id INTEGER REFERENCES events(id),
    created_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE event_members
(
    event_id INTEGER REFERENCES events(id),
    user_id INTEGER REFERENCES users(id)
);