DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  user_id IDENTITY NOT NULL PRIMARY KEY,
  email VARCHAR(255) UNIQUE,
  password CHAR(60),
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  role VARCHAR(50),
  enable BOOLEAN,
  reset_token CHAR(36)
);