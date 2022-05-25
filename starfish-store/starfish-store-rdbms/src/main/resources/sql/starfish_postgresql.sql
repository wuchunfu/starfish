-- CREATE DATABASE starfish;

DROP TABLE IF EXISTS t_sf_node_entity;
CREATE TABLE t_sf_node_entity (
  id serial NOT NULL,
  qualified_name varchar(100) not NULL,
  category varchar(100) not null,
  operator int DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  PRIMARY KEY (id)
);



DROP TABLE IF EXISTS t_sf_relation_entity;
CREATE TABLE t_sf_relation_entity (
  id serial NOT NULL,
  name varchar(100) not NULL,
  category varchar(100),
  start_node_entity_id bigint,
  end_node_entity_id bigint,
  operator int DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS t_sf_node_entity_property;
CREATE TABLE t_sf_node_entity_property (
  id serial NOT NULL,
  name varchar(100) not NULL,
  property_value json,
  entity_id bigint,
  operator int DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_sf_relation_entity_property;
CREATE TABLE t_sf_relation_entity_property (
   id serial NOT NULL,
   name varchar(100) not NULL,
   property_value json,
   entity_id bigint,
   operator int DEFAULT NULL,
   create_time timestamp DEFAULT NULL,
   update_time timestamp DEFAULT NULL,
   PRIMARY KEY (id)
);


alter table t_sf_node_entity add constraint node_entity_category_qualified_name_unique unique(category, qualified_name);
