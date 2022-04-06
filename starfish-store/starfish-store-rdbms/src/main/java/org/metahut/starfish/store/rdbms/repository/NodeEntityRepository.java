package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeEntityRepository extends JpaRepository<NodeEntity, Long> {

}
