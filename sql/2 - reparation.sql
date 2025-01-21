-- TABLES:
    DROP TABLE IF EXISTS reparation CASCADE;
    CREATE TABLE reparation(
        id SERIAL PRIMARY KEY,
        date DATE,
        price FLOAT,
        id_model INT NOT NULL REFERENCES model(id) ON DELETE CASCADE,
        id_engineer INT NOT NULL REFERENCES engineer(id) ON DELETE CASCADE
    );

    DROP TABLE IF EXISTS reparation_detail CASCADE;
    CREATE TABLE reparation_detail(
        id SERIAL PRIMARY KEY,
        id_reparation INT NOT NULL REFERENCES reparation(id) ON DELETE CASCADE,
        id_component_category INT NOT NULL REFERENCES component_category(id) ON DELETE CASCADE
    );

    DROP TABLE IF EXISTS reparation_feedback CASCADE;
    CREATE TABLE reparation_feedback(
        id SERIAL PRIMARY KEY,
        date DATE NOT NULL,
        id_customer INT NOT NULL REFERENCES customer(id) ON DELETE CASCADE,
        id_reparation INT NOT NULL REFERENCES reparation(id) ON DELETE CASCADE  
    );

    DROP TABLE IF EXISTS customer CASCADE;
    CREATE TABLE customer(
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        telephone VARCHAR(15),
        email VARCHAR(255),
        address VARCHAR(255),
        CHECK(name != ''),
        CHECK (telephone ~ '^\+?[0-9]{10,}$'),
        CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );

    DROP TABLE IF EXISTS engineer CASCADE;
    CREATE TABLE engineer(
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        telephone VARCHAR(15),
        email VARCHAR(255),
        address VARCHAR(255),
        CHECK(name != ''),
        CHECK (telephone ~ '^\+?[0-9]{10,}$'),
        CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );

-- VIEWS:
    CREATE OR REPLACE VIEW v_label_reparation AS
        SELECT
            r.id,
            r.date,
            r.price,
            r.id_model,
            m.serial_number,
            m.id_model_category,
            mc.label AS model_category,
            m.id_brand,
            b.label AS brand,
            m.description
        FROM
            reparation AS r
        JOIN
            model AS m ON r.id_model = m.id
        JOIN
            model_category AS mc ON m.id_model_category = mc.id
        JOIN
            brand AS b ON m.id_brand = b.id
    ;

    
    CREATE OR REPLACE VIEW v_label_reparation_feedback AS
        SELECT
            rf.*,
            c.name,
            vlr.date AS date_start,
            vlr.price,
            vlr.id_model,
            vlr.serial_number,
            vlr.id_model_category,
            vlr.model_category,
            vlr.id_brand,
            vlr.brand,
            vlr.description
        FROM
            reparation_feedback AS rf
        JOIN 
            customer AS c ON rf.id_customer = c.id
        JOIN 
            v_label_reparation AS vlr ON rf.id_reparation = vlr.id
    ;

    CREATE OR REPLACE VIEW AS
        SELECT
            eng.*,
            fb.date,
            price*(5/100) AS commission
        FROM
            engineer as eng
        JOIN
            reparation as rep ON rep.id_engineer = eng.id 
        JOIN 
            reparation_feedback as fb ON fb.id_reparation = rep.id
    ;
