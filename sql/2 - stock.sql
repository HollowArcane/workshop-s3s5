-- TODO: verify mvt_stock_component dn bindings

-- TABLES:
    DROP TABLE IF EXISTS supplier CASCADE;
    CREATE TABLE supplier(
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        -- digits only (eg: 0341234567 is valid, 034 12 345 67 is not)
        telephone VARCHAR(15),
        email VARCHAR(255),
        address VARCHAR(255),
        CHECK(name != ''),
        CHECK (telephone ~ '^\+?[0-9]{10,}$'),
        CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );


    DROP TABLE IF EXISTS entry_component CASCADE;
    CREATE TABLE entry_component(
        id SERIAL PRIMARY KEY,
        datetime TIMESTAMP NOT NULL,
        quantity NUMERIC(13, 2) NOT NULL,
        cost_unit NUMERIC(13, 2) NOT NULL,
        id_component INT NOT NULL REFERENCES component(id) ON DELETE CASCADE,
        id_supplier INT NOT NULL REFERENCES supplier(id) ON DELETE CASCADE,
        UNIQUE(datetime, id_component),
        CHECK(quantity > 0),
        CHECK(cost_unit > 0)
    );

    DROP TABLE IF EXISTS mvt_stock_component CASCADE;
    CREATE TABLE mvt_stock_component(
        id SERIAL PRIMARY KEY,
        -- denormalization
        quantity NUMERIC(13, 2) NOT NULL, -- dn component_entry.quantity | ticket_component_withdraw.quantity
        cost_unit NUMERIC(13, 2) NOT NULL, -- dn component_entry.cost_unit
        id_component INT NOT NULL REFERENCES component(id) ON DELETE CASCADE, -- dn component_entry.id_component
        CHECK(cost_unit >= 0)
    );

-- VIEWS:
    DROP VIEW IF EXISTS v_label_entry_component;
    CREATE OR REPLACE VIEW v_label_entry_component AS
        SELECT
            ec.id,
            ec.datetime,
            ec.quantity,
            ec.cost_unit,
            ec.id_component,
            c.serial_number,
            c.id_component_category,
            cc.label AS component_category,
            c.id_model_category,
            mc.label AS model_category,
            c.id_brand,
            b.label AS brand,
            c.description,
            ec.id_supplier,
            s.name,
            s.telephone,
            s.email,
            s.address
        FROM
            entry_component AS ec
        JOIN
            component AS c ON ec.id_component = c.id
        JOIN
            component_category AS cc ON c.id_component_category = cc.id
        JOIN
            model_category AS mc ON c.id_model_category = mc.id
        JOIN
            brand AS b ON c.id_brand = b.id
        JOIN
            supplier AS s ON ec.id_supplier = s.id
    ;
    
    DROP VIEW IF EXISTS v_label_mvt_stock_component;
    CREATE OR REPLACE VIEW v_label_mvt_stock_component AS
        SELECT
            msc.id,
            msc.quantity,
            msc.cost_unit,
            msc.id_component,
            c.serial_number,
            c.id_component_category,
            cc.label AS component_category,
            c.id_model_category,
            mc.label AS model_category,
            c.id_brand,
            b.label AS brand,
            c.description
        FROM
            mvt_stock_component AS msc
        JOIN
            component AS c ON msc.id_component = c.id
        JOIN
            component_category AS cc ON c.id_component_category = cc.id
        JOIN
            model_category AS mc ON c.id_model_category = mc.id
        JOIN
            brand AS b ON c.id_brand = b.id
    ;
