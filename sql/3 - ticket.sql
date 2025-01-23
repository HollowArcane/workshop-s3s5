-- TABLES:

    DROP TABLE IF EXISTS ticket_state CASCADE;
    CREATE TABLE ticket_state(
        id SERIAL PRIMARY KEY,
        label VARCHAR(20) NOT NULL UNIQUE,
        CHECK(label != '')
    );

    DROP TABLE IF EXISTS ticket CASCADE;
    CREATE TABLE ticket(
        id SERIAL PRIMARY KEY,
        price_reparation NUMERIC(13, 2) NOT NULL,
        id_customer INT NOT NULL REFERENCES customer(id) ON DELETE CASCADE,
        id_model INT NOT NULL REFERENCES model(id) ON DELETE CASCADE,
        diagnostic TEXT NOT NULL,
        -- denormalization
        id_ticket_state INT REFERENCES ticket_state(id) ON DELETE CASCADE, -- dn of mvt_ticket_state.id_ticket_status
        date_start DATE,
        date_end DATE,
        CHECK(price_reparation >= 0)
    );

    DROP TABLE IF EXISTS ticket_component CASCADE;
    CREATE TABLE ticket_component(
        id SERIAL PRIMARY KEY,
        id_ticket INT NOT NULL REFERENCES ticket(id) ON DELETE CASCADE,
        id_component INT NOT NULL REFERENCES component(id) ON DELETE CASCADE,
        quantity NUMERIC(13, 2) NOT NULL,
        -- denormalization
        cost_total NUMERIC(13, 2) NOT NULL, -- expected to be input, dn of entry_component
        UNIQUE(id_ticket, id_component),
        CHECK(quantity > 0)
    );

    -- expected to be generated based on ticket_component data, maybe using some strategy like FIFO or LIFO
    DROP TABLE IF EXISTS ticket_stock_withdraw CASCADE;
    CREATE TABLE ticket_stock_withdraw(
        id SERIAL PRIMARY KEY,
        id_ticket INT NOT NULL REFERENCES ticket(id) ON DELETE CASCADE,
        id_entry_component INT NOT NULL REFERENCES entry_component(id) ON DELETE CASCADE,
        quantity NUMERIC(13, 2) NOT NULL,
        UNIQUE(id_ticket, id_entry_component),
        CHECK(quantity > 0)
    );

    DROP TABLE IF EXISTS mvt_ticket_state CASCADE;
    CREATE TABLE mvt_ticket_state(
        id SERIAL PRIMARY KEY,
        datetime TIMESTAMP NOT NULL,
        id_ticket INT NOT NULL REFERENCES ticket(id) ON DELETE CASCADE,
        id_ticket_state INT NOT NULL REFERENCES ticket_state(id) ON DELETE CASCADE,
        UNIQUE(id_ticket, id_ticket_state)
    );

-- VIEWS:
    DROP VIEW IF EXISTS v_label_ticket CASCADE;
    CREATE OR REPLACE VIEW v_label_ticket AS
        SELECT
            t.id,
            t.price_reparation,
            t.id_customer,
            c.name,
            c.telephone,
            c.email,
            c.address,
            t.id_model,
            m.serial_number,
            m.id_model_category,
            mc.label AS model_category,
            m.id_brand,
            b.label AS brand,
            m.description,
            t.diagnostic,
            t.id_ticket_state,
            ts.label AS ticket_state,
            t.date_start,
            t.date_end
        FROM
            ticket AS t
        JOIN
            customer AS c ON t.id_customer = c.id
        JOIN
            model AS m ON t.id_model = m.id
        JOIN
            model_category AS mc ON m.id_model_category = mc.id
        JOIN
            brand AS b ON m.id_brand = b.id
        JOIN
            ticket_state AS ts ON t.id_ticket_state = ts.id
    ;

    DROP VIEW IF EXISTS v_state_mvt_ticket_state CASCADE;
    CREATE OR REPLACE VIEW v_state_mvt_ticket_state AS
        SELECT
            mts.id,
            mts.datetime,
            mts.id_ticket,
            mts.id_ticket_state
        FROM
            mvt_ticket_state AS mts
        JOIN (
            SELECT
                    MAX(_mts.datetime) AS datetime,
                    _mts.id_ticket
            FROM
                    mvt_ticket_state AS _mts
            GROUP BY
                    _mts.id_ticket
        ) AS mts_state ON mts.datetime = mts_state.datetime AND mts.id_ticket = mts_state.id_ticket
    ;

-- TRIGGERS:
    DROP PROCEDURE p_state_mvt_ticket_state;
    CREATE OR REPLACE PROCEDURE p_state_mvt_ticket_state(
            id_ticket_param INT,
            OUT id_ticket_status_output INT
        )
        LANGUAGE plpgsql AS $$
        BEGIN
            SELECT
                id_ticket_state
            INTO
                id_ticket_status_output
            FROM
                v_state_mvt_ticket_state mss
            WHERE
                id_ticket = id_ticket_param
            UNION ALL
            SELECT
                NULL;
        END;
    $$;

    DROP FUNCTION fn_state_mvt_ticket_state;
    CREATE OR REPLACE FUNCTION fn_state_mvt_ticket_state()
        RETURNS TRIGGER AS $$
        DECLARE
            id_ticket_status_param INT;
        BEGIN
            IF TG_OP = 'UPDATE' OR TG_OP = 'INSERT' THEN
                CALL p_state_mvt_ticket_state(NEW.id_ticket, id_ticket_status_param);

                UPDATE ticket
                SET
                    id_ticket_status = id_ticket_status_param
                WHERE
                    id = NEW.id_ticket;
            END IF;

            IF TG_OP = 'UPDATE' OR TG_OP = 'DELETE' THEN
                CALL p_state_mvt_ticket_state(OLD.id_ticket, id_ticket_status_param);

                UPDATE ticket
                SET
                    id_ticket_status = id_ticket_status_param
                WHERE
                    id = NEW.id_ticket;
            END IF;

            RETURN NEW;
        END;
    $$ LANGUAGE plpgsql;

    CREATE OR REPLACE TRIGGER t_state_mvt_ticket_state
        AFTER INSERT OR UPDATE OR DELETE ON mvt_ticket_state
        FOR EACH ROW
        EXECUTE FUNCTION fn_state_mvt_ticket_state();

-- CONSTANTS:
    INSERT INTO ticket_state(id, label) VALUES
    (1, 'En Attente'),
    (2, 'Diagnostic'),
    (3, 'Réparation'),
    (4, 'Terminé'),
    (5, 'Récupéré');