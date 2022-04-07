package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.dao.INodeEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class NodeEntityMapper implements INodeEntityMapper<NodeEntity, NodeEntityProperty> {

    @Autowired
    private NodeEntityRepository nodeEntityRepository;

    @Override
    public NodeEntity create(NodeEntity entity) {
        return nodeEntityRepository.save(entity);
    }

    @Override
    public Collection<NodeEntity> createBatch(Collection<NodeEntity> entities) {
        return nodeEntityRepository.saveAll(entities);
    }

    @Override
    public void remove(NodeEntity entity) {
        nodeEntityRepository.delete(entity);
    }

    @Override
    public void removeBatch(Collection<NodeEntity> entities) {
        nodeEntityRepository.deleteAll(entities);
    }

    @Override
    public void removeAll() {
        nodeEntityRepository.deleteAll();
    }
}
