CREATE TABLE IF NOT EXISTS roles (
  role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id BIGINT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS training_classes (
  class_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  class_name VARCHAR(255) NOT NULL,
  place VARCHAR(255) NULL,
  duration_in_minutes INT DEFAULT 0,
  description VARCHAR(255) NOT NULL,
  category ENUM('DANCE', 'AEROBICS', 'YOGA', 'MARTIAL_ARTS') NOT NULL
);

CREATE TABLE IF NOT EXISTS trainers (
  trainer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS sessions (
  session_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  from_date TIMESTAMP NOT NULL,
  to_date TIMESTAMP NOT NULL,
  capacity INT NOT NULL,
  session_date DATE NOT NULL,
  class_id BIGINT NOT NULL,
  FOREIGN KEY (class_id) REFERENCES training_classes(class_id)
);

CREATE TABLE IF NOT EXISTS class_trainers (
  class_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  PRIMARY KEY (class_id, trainer_id),
  FOREIGN KEY (class_id) REFERENCES training_classes(class_id),
  FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);

CREATE TABLE IF NOT EXISTS session_user (
  session_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (session_id, user_id),
  FOREIGN KEY (session_id) REFERENCES sessions(session_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS session_trainer (
  session_id BIGINT NOT NULL,
  trainer_id BIGINT NOT NULL,
  PRIMARY KEY (session_id, trainer_id),
  FOREIGN KEY (session_id) REFERENCES sessions(session_id),
  FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);
