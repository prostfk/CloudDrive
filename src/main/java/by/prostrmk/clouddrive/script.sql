-- #  CREATE TABLE ItNews.OfferArticles (
-- #   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
-- #   type varchar(20),
-- #   title varchar(255),
-- #   content text,
-- #   userName varchar(40),
-- #   pathToFile VARCHAR(255)
-- #  )
--
-- ALTER TABLE users DROP COLUMN access;
--
-- # CREATE TABLE users(
-- #   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
-- #   username VARCHAR(255) NOT NULL ,
-- #   password VARCHAR(255) NOT NULL
-- # );
--
-- # CREATE TABLE roles(
-- #   id INT NOT NULL  AUTO_INCREMENT PRIMARY KEY ,
-- #   name VARCHAR(100) NOT NULL
-- # );
--
-- # CREATE TABLE user_roles(
-- #   user_id INT NOT NULL,
-- #   role_id INT NOT NULL,
-- #
-- #   FOREIGN KEY (user_id) REFERENCES users(id),
-- #   FOREIGN KEY (role_id) REFERENCES roles(id),
-- #   UNIQUE (user_id,role_id)
-- #
-- # );
-- #
-- #
-- # INSERT into users VALUES (1,'roman','$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
-- # INSERT INTO roles VALUES (1,'ROLE_USER');
-- # INSERT INTO roles VALUES (2,'ROLE_ADMIN');
-- # INSERT INTO user_roles VALUES (1,2);
--
-- # CREATE TABLE comments(
-- #   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
-- #   article_id INT NOT NULL,
-- #   username varchar(255) NOT NULL,
-- #   comment varchar(255) NOT NULL,
-- #   date varchar(25) NOT NULL
-- # )
CREATE TABLE CloudDrive.Users(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(255),
  password varchar(255)
)
