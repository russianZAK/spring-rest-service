CREATE SEQUENCE street_id_sequence;
CREATE SEQUENCE house_id_sequence;
CREATE SEQUENCE road_surface_id_sequence;

CREATE TABLE street (
    id INT PRIMARY KEY DEFAULT nextval('street_id_sequence'),
    name VARCHAR(255) NOT NULL,
    postal_code BIGINT NOT NULL UNIQUE
);

CREATE TABLE house (
    id INT PRIMARY KEY DEFAULT nextval('house_id_sequence'),
    house_number VARCHAR(255) NOT NULL,
    build_date DATE NOT NULL,
    num_floors INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    street_id INT NOT NULL,
    FOREIGN KEY (street_id) REFERENCES street(id),
    UNIQUE (house_number, street_id)
);

CREATE TABLE road_surface (
    id INT PRIMARY KEY DEFAULT nextval('road_surface_id_sequence'),
    type VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    friction_coefficient DOUBLE PRECISION NOT NULL
);

CREATE TABLE road_surface_street (
    road_surface_id INT,
    street_id INT,
    PRIMARY KEY (road_surface_id, street_id),
    FOREIGN KEY (road_surface_id) REFERENCES road_surface(id),
    FOREIGN KEY (street_id) REFERENCES street(id)
);
