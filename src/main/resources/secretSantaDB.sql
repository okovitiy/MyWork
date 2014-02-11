CREATE TABLE groups (
  id_group     INT AUTO_INCREMENT,
  name         VARCHAR(128) NOT NULL,
  PRIMARY KEY(id_group)
);

CREATE TABLE users (
  id_user      INT AUTO_INCREMENT,
  name         VARCHAR(128) NOT NULL,
  id_group     INT (10) NOT NULL,
  PRIMARY KEY (id_user),
  FOREIGN KEY (id_group) REFERENCES groups (id_group)
);

CREATE TABLE presents (
  id_present   INT AUTO_INCREMENT,
  title        VARCHAR(128) NOT NULL,
  PRIMARY KEY(id_present)
);

CREATE TABLE user_present (
  id_user      INT (10) NOT NULL,
  id_present   INT (10) NOT NULL,
  FOREIGN KEY (id_user) REFERENCES users (id_user),
  FOREIGN KEY (id_present) REFERENCES presents (id_present)
);

CREATE TABLE pairs (
  giver_id     INT (10) NOT NULL,
  receiver_id  INT (10) NOT NULL,
  FOREIGN KEY (giver_id)    REFERENCES users (id_user),
  FOREIGN KEY (receiver_id) REFERENCES users (id_user)
);





