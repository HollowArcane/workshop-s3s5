-- tables
CREATE TABLE reparation(
   id SERIAL PRIMARY KEY,
    date DATE,
    price DOUBLE,
    id_model NOT NULL INT REFERENCES model(id) ON DELETE CASCADE
);

CREATE TABLE reparation_detail(
   id SERIAL PRIMARY KEY,
    id_reparation NOT NULL INT REFERENCES reparation(id) ON DELETE CASCADE,
    id_model_category NOT NULL INT REFERENCES model_category(id) ON DELETE CASCADE,
);

