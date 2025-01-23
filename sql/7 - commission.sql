-- TABLES:

DROP TABLE IF EXISTS gender CASCADE;
CREATE TABLE gender(
    id SERIAL PRIMARY KEY,
    gender VARCHAR(55) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS engineer CASCADE;
CREATE TABLE engineer(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(15),
    email VARCHAR(255),
    address VARCHAR(255),
    id_gender INT NOT NULL REFERENCES gender(id) ON DELETE CASCADE,
    CHECK(name != ''),
    CHECK (telephone ~ '^\+?[0-9]{10,}$'),
    CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

