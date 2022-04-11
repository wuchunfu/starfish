package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.dao.IEntityPropertyMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityPropertyRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class NodeEntityPropertyMapper implements
    IEntityPropertyMapper<Long, NodeEntity, NodeEntityProperty> {

    @Autowired
    private NodeEntityPropertyRepository repository;

    @Override
    public NodeEntityProperty create(NodeEntityProperty property) {
        property.setId(null);
        return repository.save(property);
    }

    @Override
    public Collection<NodeEntityProperty> createBatch(Collection<NodeEntityProperty> properties) {
        properties.stream().forEach(property -> {
            property.setId(null);
        });
        return repository.saveAll(properties);
    }

    @Override
    public void remove(NodeEntityProperty property) {
        repository.delete(property);
    }

    @Override
    public void removeBatch(Collection<NodeEntityProperty> properties) {
        repository.deleteAll(properties);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public void removeBatchById(Collection<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void removeAllByName(String name) {
        repository.removeByName(name);
    }

    @Override
    public void removeAllByName(NodeEntityProperty property) {
        repository.removeByName(property.getName());
    }

    @Override
    public NodeEntityProperty update(NodeEntityProperty property) {
        return repository.save(property);
    }

    @Override
    public Collection<NodeEntityProperty> updateBatchById(Collection<NodeEntityProperty> properties) {
        return repository.saveAll(properties);
    }

    @Override
    public NodeEntityProperty findById(Long id) {
        return repository.getById(id);
    }

    @Override
    public NodeEntityProperty findById(NodeEntityProperty property) {
        return findById(property.getId());
    }

    @Override
    public Collection<NodeEntityProperty> findAllById(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Collection<NodeEntityProperty> findByName(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");

        NodeEntityProperty property = new NodeEntityProperty();
        property.setName(name);

        return repository.findAll(Example.of(property, matcher));
    }

    @Override
    public Page<NodeEntityProperty> findByName(String name, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");

        NodeEntityProperty property = new NodeEntityProperty();
        property.setName(name);

        return repository.findAll(Example.of(property, matcher), pageable);
    }
}
