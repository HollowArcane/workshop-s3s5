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

DROP VIEW v_engineer_commission CASCADE;
CREATE OR REPLACE VIEW v_engineer_commission AS
    SELECT
        eng.*,
        fb.date,
        gd.gender,
        CASE
            WHEN price >= 200000 THEN (price*5)/100
            ELSE 0
        END AS commission
    FROM
        engineer as eng
    JOIN
        reparation as rep ON rep.id_engineer = eng.id 
    JOIN 
        reparation_feedback as fb ON fb.id_reparation = rep.id
    JOIN 
        gender as gd ON gd.id = eng.id_gender
;

