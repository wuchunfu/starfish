-- CREATE DATABASE starfish DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

DROP TABLE IF EXISTS t_sf_node_entity;
CREATE TABLE t_sf_node_entity (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    category varchar(100) not null,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS t_sf_relation_entity;
CREATE TABLE t_sf_relation_entity (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    category varchar(100),
    start_node_entity_id bigint,
    end_node_entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS t_sf_node_entity_property;
CREATE TABLE t_sf_node_entity_property (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    property_value json,
    entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_sf_relation_entity_property;
CREATE TABLE t_sf_relation_entity_property (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(100) not NULL,
    property_value json,
    entity_id bigint,
    operator int DEFAULT NULL,
    create_time datetime NULL,
    update_time datetime NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
