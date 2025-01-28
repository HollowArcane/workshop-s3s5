-- TODO: verify receipt and receipt_row dn bindings


-- TABLES:
    DROP TABLE IF EXISTS receipt_type CASCADE;
    CREATE TABLE receipt_type(
        id SERIAL PRIMARY KEY,
        label VARCHAR(20) NOT NULL UNIQUE,
        CHECK(label != '')
    );

    -- expected to be generated, not updated automatically
    DROP TABLE IF EXISTS receipt CASCADE;
    CREATE TABLE receipt(
        id SERIAL PRIMARY KEY,
        datetime TIMESTAMP NOT NULL,
        id_receipt_type INT NOT NULL REFERENCES receipt_type(id) ON DELETE CASCADE,
        -- denormalization of ticket
        price_total_component NUMERIC(13, 2) NOT NULL,
        price_total_employee NUMERIC(13, 2) NOT NULL,
        price_reparation NUMERIC(13, 2) NOT NULL,
        taxe_percentage NUMERIC(5, 4) NOT NULL,
        id_customer INT NOT NULL REFERENCES customer(id) ON DELETE CASCADE  
    );

    DROP TABLE IF EXISTS receipt_row CASCADE;
    CREATE TABLE receipt_row(
        id SERIAL PRIMARY KEY,
        id_receipt INT NOT NULL REFERENCES receipt(id) ON DELETE CASCADE,
        -- denormalization of ticket_stock_withdraw
        id_component INT NOT NULL REFERENCES component(id) ON DELETE CASCADE,
        cost_unit NUMERIC(13, 2) NOT NULL,
        quantity NUMERIC(13, 2) NOT NULL
    );

-- VIEWS:
    DROP VIEW IF EXISTS v_label_receipt CASCADE;
    CREATE OR REPLACE VIEW v_label_receipt AS
        SELECT
            r.id,
            r.datetime,
            r.id_receipt_type,
            rt.label AS receipt_type,
            r.price_total_component,
            r.price_total_employee,
            r.price_reparation,
            r.taxe_percentage,
            r.id_customer,
            c.name,
            c.telephone,
            c.email,
            c.address
        FROM
            receipt AS r
        JOIN
            receipt_type AS rt ON r.id_receipt_type = rt.id
        JOIN
            customer AS c ON r.id_customer = c.id
    ;
