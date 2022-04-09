package org.metahut.starfish.store.rdbms.dao;

import java.util.Collection;
import java.util.List;
import org.metahut.starfish.store.dao.INodeEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepository;
import org.metahut.starfish.store.rdbms.repository.RelationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class NodeEntityMapper implements INodeEntityMapper<Long, NodeEntity, NodeEntityProperty> {

    @Autowired
    private NodeEntityRepository repository;

    @Autowired
    private RelationEntityRepository relationEntityRepository;

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
        relationEntityRepository.removeByStartNodeEntity(entity);
        relationEntityRepository.removeByEndNodeEntity(entity);
    }

    @Override
    public void removeBatch(Collection<NodeEntity> entities) {
        repository.deleteAll(entities);
        entities.stream().forEach(entity -> {
            relationEntityRepository.removeByStartNodeEntity(entity);
            relationEntityRepository.removeByEndNodeEntity(entity);
        });
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
        relationEntityRepository.deleteAll();
    }

    @Override
    public void removeBatchById(Collection<Long> ids) {
        repository.deleteAllById(ids);
        ids.stream().forEach(id -> {
            NodeEntity entity = new NodeEntity();
            entity.setId(id);
            relationEntityRepository.removeByStartNodeEntity(entity);
            relationEntityRepository.removeByEndNodeEntity(entity);
        });

    }

    @Override
    public void removeAllByName(String name) {
        List<NodeEntity> entities = repository.removeByName(name);
        entities.stream().forEach(entity -> {
            relationEntityRepository.removeByStartNodeEntity(entity);
            relationEntityRepository.removeByEndNodeEntity(entity);
        });
    }

    @Override
    public void removeAllByName(NodeEntity entity) {
        removeAllByName(entity.getName());
    }

    @Override
    public void removeAllByCategory(String category) {
        List<NodeEntity> entities = repository.removeByCategory(category);
        entities.stream().forEach(entity -> {
            relationEntityRepository.removeByStartNodeEntity(entity);
            relationEntityRepository.removeByEndNodeEntity(entity);
        });
    }

    @Override
    public void removeAllByCategory(NodeEntity entity) {
        removeAllByCategory(entity.getCategory());
    }

    @Override
    public NodeEntity update(NodeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Collection<NodeEntity> updateBatchById(Collection<NodeEntity> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public NodeEntity findById(Long id) {
        return repository.getById(id);
    }

    @Override
    public NodeEntity findById(NodeEntity entity) {
        return findById(entity.getId());
    }

    @Override
    public List<NodeEntity> findAllById(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<NodeEntity> findByName(String name) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<NodeEntity> findByCategory(String category) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setCategory(category);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("category");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<NodeEntity> findByCategoryAndName(String category, String name) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setCategory(category);
        nodeEntity.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("category", "name");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example);
    }

    @Override
    public Page<NodeEntity> findByName(String name, Pageable pageable) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example, pageable);
    }

    @Override
    public Page<NodeEntity> findByCategory(String category, Pageable pageable) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setCategory(category);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("category");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example, pageable);
    }

    @Override
    public Page<NodeEntity> findByCategoryAndName(String category, String name, Pageable pageable) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setCategory(category);
        nodeEntity.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("category", "name");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findAll(example, pageable);
    }
}
