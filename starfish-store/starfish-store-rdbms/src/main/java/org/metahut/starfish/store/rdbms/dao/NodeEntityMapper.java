package org.metahut.starfish.store.rdbms.dao;

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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class NodeEntityMapper implements INodeEntityMapper<Long, NodeEntity, NodeEntityProperty> {

    @Autowired
    private NodeEntityRepository repository;

    @Autowired
    private RelationEntityRepository relationEntityRepository;

    @Override
    public NodeEntity create(NodeEntity entity) {
        entity.setId(null);
        if (!CollectionUtils.isEmpty(entity.getProperties())) {
            entity.getProperties().stream().forEach(property -> {
                property.setEntity(entity);
            });
        }
        return repository.saveAndFlush(entity);
    }

    @Override
    public Collection<NodeEntity> createBatch(Collection<NodeEntity> entities) {
        if (!CollectionUtils.isEmpty(entities)) {
            entities.stream().forEach(entity -> {
                entity.setId(null);
                if (!CollectionUtils.isEmpty(entity.getProperties())) {
                    entity.getProperties().stream().forEach(property -> {
                        property.setEntity(entity);
                    });
                }
            });
            return repository.saveAll(entities);
        }
        return entities;
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
    public void removeAllByQualifiedName(String qualifiedName) {
        List<NodeEntity> entities = repository.removeByQualifiedName(qualifiedName);
        entities.stream().forEach(entity -> {
            relationEntityRepository.removeByStartNodeEntity(entity);
            relationEntityRepository.removeByEndNodeEntity(entity);
        });
    }

    @Override
    public void removeAllByQualifiedName(NodeEntity entity) {
        removeAllByQualifiedName(entity.getQualifiedName());
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
    public Page<NodeEntity> findAllById(Collection<Long> ids, Pageable pageable) {
        return repository.findALlByIdIn(ids,pageable);
    }

    @Override
    public List<NodeEntity> findByQualifiedName(String qualifiedName) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setQualifiedName(qualifiedName);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("qualifiedName");
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
    public NodeEntity findByCategoryAndQualifiedName(String category, String qualifiedName) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setCategory(category);
        nodeEntity.setQualifiedName(qualifiedName);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("category", "qualifiedName");
        Example<NodeEntity> example = Example.of(nodeEntity, matcher);
        return repository.findOne(example).orElse(null);
    }

    @Override
    public Page<NodeEntity> findByQualifiedName(String qualifiedName, Pageable pageable) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setQualifiedName(qualifiedName);
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("qualifiedName");
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
    public Optional<NodeEntity> findOne(Specification<NodeEntity> spec) {
        return repository.findOne(spec);
    }

    @Override
    public List<NodeEntity> findAll(Specification<NodeEntity> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Page<NodeEntity> findAll(Specification<NodeEntity> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<NodeEntity> findAll(Specification<NodeEntity> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    @Override
    public long count(Specification<NodeEntity> spec) {
        return repository.count(spec);
    }
}
