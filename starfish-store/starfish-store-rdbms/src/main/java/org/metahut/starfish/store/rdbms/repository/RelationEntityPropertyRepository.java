package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.RelationEntityProperty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationEntityPropertyRepository extends JpaRepository<RelationEntityProperty, Long>,
    JpaSpecificationExecutor<RelationEntityProperty> {

    List<RelationEntityProperty> removeByName(String name);

}
