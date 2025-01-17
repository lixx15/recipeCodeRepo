
DROP DATABASE recipe;
CREATE DATABASE recipe;
DROP USER 'recipe_root'@'localhost';
CREATE USER 'recipe_root'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON recipe.* TO 'recipe_root'@'localhost';
FLUSH PRIVILEGES;
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
('Alice', 'password1', 'alice@example.com'),
('Bob', 'password2', 'bob@example.com'),
('Charlie', 'password3', 'charlie@example.com'),
('David', 'password4', 'david@example.com'),
('Eve', 'password5', 'eve@example.com');

INSERT INTO ingredients (name, quantity, recipe_id) VALUES
('米', '2合', 1), -- レシピID 1: おにぎり
('海苔', '3枚', 1),
('梅干し', '2個', 1),
('うどん', '200g', 2), -- レシピID 2: かけうどん
('つゆ', '300ml', 2),
('ネギ', '適量', 2),
('鮭', '1切れ', 3), -- レシピID 3: 焼き鮭
('塩', '少々', 3),
('しょうゆ', '適量', 3),
('鶏もも肉', '300g', 4), -- レシピID 4: 親子丼
('卵', '2個', 4),
('玉ねぎ', '1個', 4),
('ご飯', '2杯', 4),
('キャベツ', '1/4玉', 5), -- レシピID 5: 焼きそば
('豚バラ肉', '150g', 5),
('焼きそばソース', '100ml', 5),
('紅しょうが', '適量', 5);

-- Inserting data into recipes
INSERT INTO recipes (title, recipe_description, procedure_description, user_id) VALUES
('おにぎり', '簡単で美味しいおにぎりの作り方', '1. ご飯を炊く\n2. 好きな具材を入れる\n3. 海苔で巻く', 1),
('かけうどん', 'シンプルな和風のうどん', '1. うどんを茹でる\n2. つゆを温める\n3. うどんに注いでネギを添える', 2),
('焼き鮭', '朝食にぴったりの焼き鮭', '1. 鮭に塩を振る\n2. フライパンで焼く\n3. しょうゆを少しかける', 3),
('親子丼', '鶏肉と卵を使った丼物', '1. 玉ねぎを炒める\n2. 鶏肉を加えて煮る\n3. 卵を溶いてかける', 4),
('焼きそば', '野菜たっぷりの焼きそば', '1. キャベツと豚肉を炒める\n2. 焼きそば麺を加える\n3. ソースで味付けして紅しょうがを添える', 5);

