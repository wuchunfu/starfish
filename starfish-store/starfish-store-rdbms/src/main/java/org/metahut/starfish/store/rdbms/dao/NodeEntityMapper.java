package org.metahut.starfish.store.rdbms.dao;

import java.util.Collection;
import org.metahut.starfish.store.dao.INodeEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NodeEntityMapper implements INodeEntityMapper<NodeEntity, NodeEntityProperty> {

    @Autowired
    private NodeEntityRepository repository;

    @Override
    public NodeEntity create(NodeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Collection<NodeEntity> createBatch(Collection<NodeEntity> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void remove(NodeEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void removeBatch(Collection<NodeEntity> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }
}
