-- tables
CREATE TABLE reparation(
   id SERIAL PRIMARY KEY,
    date DATE,
    price FLOAT,
    id_model INT NOT NULL REFERENCES model(id) ON DELETE CASCADE
);

CREATE TABLE reparation_detail(
   id SERIAL PRIMARY KEY,
    id_reparation INT NOT NULL REFERENCES reparation(id) ON DELETE CASCADE,
    id_component_category INT NOT NULL REFERENCES component_category(id) ON DELETE CASCADE
);

