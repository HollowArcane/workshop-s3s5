
CREATE TABLE recommendation_component(
    id SERIAL PRIMARY KEY,
    id_component INT NOT NULL REFERENCES component(id) ON DELETE CASCADE,
    date_start DATE NOT NULL,
    date_end DATE NOT NULL
);

CREATE VIEW v_label_recommendation_component AS
    SELECT *
    FROM
        recommendation_component AS rec
    JOIN 
        v_label_component AS v ON rec.id_component = v.id
;