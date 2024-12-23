CREATE DATABASE recipe; 
CREATE USER 'recipe_root'@'localhost' IDENTIFIED BY '123456'; 
GRANT ALL ON recipe . * TO ' recipe_root '@'localhost'; 