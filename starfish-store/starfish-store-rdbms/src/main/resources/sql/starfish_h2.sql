SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS t_sf_node_entity CASCADE;
CREATE TABLE t_sf_node_entity
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    category varchar(100) not null,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_sf_relation_entity CASCADE;
CREATE TABLE t_sf_relation_entity
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    category varchar(100),
    start_node_entity_id bigint,
    end_node_entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_sf_node_entity_property CASCADE;
CREATE TABLE t_sf_node_entity_property
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    property_value json,
    entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_sf_relation_entity_property CASCADE;
CREATE TABLE t_sf_relation_entity_property
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    property_value json,
    entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
);