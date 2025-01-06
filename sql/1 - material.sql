CREATE DATABASE workshop;
\c workshop;

-- tables
    CREATE TABLE component_category(
        id SERIAL PRIMARY KEY,
        label VARCHAR(20) NOT NULL
    );

    CREATE TABLE model_category(
        id SERIAL PRIMARY KEY,
        label VARCHAR(20) NOT NULL
    );

    CREATE TABLE brand(
        id SERIAL PRIMARY KEY,
        label VARCHAR(20) NOT NULL
    );

    CREATE TABLE component(
        id SERIAL PRIMARY KEY,
        serial_number VARCHAR(50) NOT NULL,
        id_component_category INT NOT NULL REFERENCES component_category(id) ON DELETE CASCADE,
        -- modèle compatible
        id_model_category INT NOT NULL REFERENCES model_category(id) ON DELETE CASCADE,
        id_brand INT NOT NULL REFERENCES brand(id) ON DELETE CASCADE,
        description TEXT
    );

    CREATE TABLE model(
        id SERIAL PRIMARY KEY,
        serial_number VARCHAR(50) NOT NULL,
        id_model_category INT NOT NULL REFERENCES model_category(id) ON DELETE CASCADE,
        id_brand INT NOT NULL REFERENCES brand(id) ON DELETE CASCADE,
        description TEXT
    );

-- vues
    CREATE OR REPLACE VIEW v_label_component AS
        SELECT
            c.id,
            c.serial_number,
            c.id_component_category,
            cc.label AS component_category,
            c.id_model_category,
            mc.label AS model_category,
            c.id_brand,
            b.label AS brand,
            c.description
        FROM
            component AS c
        JOIN
            component_category AS cc ON c.id_component_category = cc.id
        JOIN
            model_category AS mc ON c.id_model_category = mc.id
        JOIN
            brand AS b ON c.id_brand = b.id
    ;

    CREATE OR REPLACE VIEW v_label_model AS
        SELECT
            m.id,
            m.serial_number,
            m.id_model_category,
            mc.label AS model_category,
            m.id_brand,
            b.label AS brand,
            m.description
        FROM
            model AS m
        JOIN
            model_category AS mc ON m.id_model_category = mc.id
        JOIN
            brand AS b ON m.id_brand = b.id
    ;