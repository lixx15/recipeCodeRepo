-- ユーザーテーブル (users)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20) NOT NULL,
    password VARCHAR(16) NOT NULL,
    email VARCHAR(50)
);

-- レシピテーブル (recipes)
CREATE TABLE recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    recipe_description VARCHAR(100) NOT NULL,
    procedure_description VARCHAR(1000) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 材料テーブル (ingredients)
CREATE TABLE ingredients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    quantity VARCHAR(20) NOT NULL,
    recipe_id INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);

-- タグテーブル (tags)
CREATE TABLE tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- タグ中間テーブル (recipe_tags)
CREATE TABLE recipe_tags (
    tag_id INT NOT NULL,
    recipe_id INT NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    PRIMARY KEY (tag_id, recipe_id)
);

-- コメントテーブル (comments)
CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(250) NOT NULL,
    post_datetime DATETIME NOT NULL,
    recipe_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- お気に入りテーブル (favorites)
CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    favorite_datetime DATETIME NOT NULL,
    recipe_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Inserting data into users
INSERT INTO users (user_name, password, email) VALUES 
('user1', 'pass1', 'user1@example.com'),
('user2', 'pass2', 'user2@example.com'),
('user3', 'pass3', 'user3@example.com'),
('user4', 'pass4', 'user4@example.com'),
('user5', 'pass5', 'user5@example.com');

-- Inserting data into recipes
INSERT INTO recipes (title, recipe_description, procedure_description, user_id) VALUES 
('Recipe1', 'Description1', 'Procedure1', 1),
('Recipe2', 'Description2', 'Procedure2', 2),
('Recipe3', 'Description3', 'Procedure3', 3),
('Recipe4', 'Description4', 'Procedure4', 4),
('Recipe5', 'Description5', 'Procedure5', 5);