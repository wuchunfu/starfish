package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeEntityPropertyRepository extends JpaRepository<NodeEntityProperty, Long>,
    JpaSpecificationExecutor<NodeEntityProperty> {

    List<NodeEntityProperty> removeByName(String name);
}
