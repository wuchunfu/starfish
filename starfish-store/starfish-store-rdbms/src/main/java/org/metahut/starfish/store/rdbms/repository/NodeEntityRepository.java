package org.metahut.starfish.store.rdbms.repository;

import java.util.List;
import javax.xml.soap.Node;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeEntityRepository extends JpaRepository<NodeEntity, Long>,
    JpaSpecificationExecutor<NodeEntity> {

    List<NodeEntity> removeByName(String name);

    List<NodeEntity> removeByCategory(String category);

}
