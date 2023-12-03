CREATE DATABASE IF NOT EXISTS `boxingfever`;

-- Use the extreme-loving database
USE `boxingfever`;

CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON boxingfever.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS `user` (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES role (role_id)
);

-- Role Table
CREATE TABLE IF NOT EXISTS `role` (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Trainer Table
CREATE TABLE IF NOT EXISTS `trainer` (
    trainer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- TrainingClass Table
CREATE TABLE IF NOT EXISTS `training_class` (
    class_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    duration_in_minutes INT DEFAULT 0 NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    PRIMARY KEY (class_id)
);

-- Session Table
CREATE TABLE IF NOT EXISTS `session` (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_date TIMESTAMP NOT NULL,
    to_date TIMESTAMP NOT NULL,
    capacity INT NOT NULL,
    session_date DATE NOT NULL,
    class_id BIGINT,
    PRIMARY KEY (session_id),
    FOREIGN KEY (class_id) REFERENCES training_class (class_id)
);

-- Session_User Table
CREATE TABLE IF NOT EXISTS `session_user` (
    session_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (session_id, user_id),
    FOREIGN KEY (session_id) REFERENCES session (session_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

-- Session_Trainer Table
CREATE TABLE IF NOT EXISTS `session_trainer` (
    session_id BIGINT,
    trainer_id BIGINT,
    PRIMARY KEY (session_id, trainer_id),
    FOREIGN KEY (session_id) REFERENCES session (session_id),
    FOREIGN KEY (trainer_id) REFERENCES trainer (trainer_id)
);