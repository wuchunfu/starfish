package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationEntityRepository extends JpaRepository<RelationEntity, Long> {

}
