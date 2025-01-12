DROP DATABASE recipe;
CREATE DATABASE recipe;
DROP USER 'recipe_root'@'localhost';
CREATE USER 'recipe_root'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON recipe.* TO 'recipe_root'@'localhost';
FLUSH PRIVILEGES;