DROP TABLE IF EXISTS persons;
 
CREATE TABLE persons (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  login VARCHAR(250) NOT NULL,
  passhash VARCHAR(250) NOT NULL,
  info VARCHAR(250) DEFAULT NULL
);
 
INSERT INTO persons (login, passhash, info) VALUES
  ('paul', 'aze', 'main'),
  ('lise', 'aze', ''),
  ('bill', 'aze', '');