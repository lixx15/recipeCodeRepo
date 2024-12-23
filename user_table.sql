DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);


INSERT INTO users (username, email, password)
VALUES ('testuser', 'test@example.com', 'hashedpassword123');