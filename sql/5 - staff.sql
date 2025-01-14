-- TODO: verify staff_ticket dn bindings


-- TABLES:
    DROP TABLE IF EXISTS staff_position CASCADE;
    CREATE TABLE staff_position(
        id SERIAL PRIMARY KEY,
        label VARCHAR(50) NOT NULL UNIQUE,
        CHECK(label != '')
    );

    DROP TABLE IF EXISTS staff CASCADE;
    CREATE TABLE staff(
        id SERIAL PRIMARY KEY,
        date_hire DATE NOT NULL UNIQUE,
        name VARCHAR(255) NOT NULL,
        telephone VARCHAR(15),
        email VARCHAR(15),
        address VARCHAR(255),
        salary_hour NUMERIC(13, 2) NOT NULL,
        id_staff_position INT NOT NULL REFERENCES staff_position(id) ON DELETE CASCADE
        CHECK(name != ''),
        CHECK (telephone ~ '^\+?[0-9]{10,}$'),
        CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );

    DROP TABLE IF EXISTS staff_ticket CASCADE;
    CREATE TABLE staff_ticket(
        id SERIAL PRIMARY KEY,
        id_staff INT NOT NULL REFERENCES staff(id) ON DELETE CASCADE,
        id_ticket INT NOT NULL REFERENCES ticket(id) ON DELETE CASCADE,
        duration_hour NUMERIC(13, 2) NOT NULL,
        -- denormalization
        salary_total NUMERIC(13, 2) NOT NULL, -- dn of staff.salary_hour
        UNIQUE(id_staff, id_ticket)
    );

-- VIEWS:
    DROP VIEW IF EXISTS v_label_staff CASCADE;
    CREATE OR REPLACE VIEW v_label_staff AS
        SELECT
            s.id,
            s.date_hire,
            s.name,
            s.telephone,
            s.email,
            s.address,
            s.salary_hour,
            s.id_staff_position,
            sp.label AS staff_position
        FROM
            staff AS s
        JOIN
            staff_position AS sp ON s.id_staff_position = sp.id
    ;
