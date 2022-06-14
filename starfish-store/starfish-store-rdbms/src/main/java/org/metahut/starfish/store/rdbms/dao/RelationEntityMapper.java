package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.dao.IRelationEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntityProperty;
import org.metahut.starfish.store.rdbms.repository.RelationEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class RelationEntityMapper implements IRelationEntityMapper<Long, RelationEntity, RelationEntityProperty, NodeEntity> {

    @Autowired
    private RelationEntityRepository repository;

    @Override
    public RelationEntity create(RelationEntity entity) {
        entity.setId(null);
        if (!CollectionUtils.isEmpty(entity.getProperties())) {
            entity.getProperties().stream().forEach(property -> {
                property.setEntity(entity);
            });
        }
        return repository.save(entity);
    }

    @Override
    public Collection<RelationEntity> createBatch(Collection<RelationEntity> entities) {
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
    public void remove(RelationEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void removeBatch(Collection<RelationEntity> entities) {
        repository.deleteAllInBatch(entities);
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
    public void removeAllByName(RelationEntity entity) {
        repository.removeByName(entity.getName());
    }

    @Override
    public void removeAllByCategory(String category) {
        repository.removeByCategory(category);
    }

    @Override
    public void removeAllByCategory(RelationEntity entity) {
        repository.removeByCategory(entity.getCategory());
    }

    @Override
    public void removeAllByNodeEntity(NodeEntity nodeEntity) {
        repository.removeByStartNodeEntity(nodeEntity);
        repository.removeByEndNodeEntity(nodeEntity);
    }

    @Override
    public void removeAllByStartNodeEntity(NodeEntity startNodeEntity) {
        repository.removeByStartNodeEntity(startNodeEntity);
    }

    @Override
    public void removeAllByEndNodeEntity(NodeEntity endNodeEntity) {
        repository.removeByEndNodeEntity(endNodeEntity);
    }

    @Override
    public void removeAllByStartNodeEntityAndEndNodeEntity(NodeEntity startNodeEntity,
        NodeEntity endNodeEntity) {
        repository.removeByStartNodeEntityAndEndNodeEntity(startNodeEntity, endNodeEntity);
    }

    @Override
    public void removeAllByStartNodeEntityAndEndNodeEntityAndCategory(NodeEntity startNodeEntity,
        NodeEntity endNodeEntity, String category) {
        repository.removeByStartNodeEntityAndEndNodeEntityAndCategory(startNodeEntity, endNodeEntity, category);
    }

    @Override
    public RelationEntity update(RelationEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Collection<RelationEntity> updateBatchById(Collection<RelationEntity> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public RelationEntity findById(Long id) {
        return repository.getById(id);
    }

    @Override
    public RelationEntity findById(RelationEntity entity) {
        return repository.getById(entity.getId());
    }

    @Override
    public Collection<RelationEntity> findAllById(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Collection<RelationEntity> findByName(String name) {
        RelationEntity entity = new RelationEntity();
        entity.setName(name);
        return repository.findAll(Example.of(entity));
    }

    @Override
    public Collection<RelationEntity> findByCategory(String category) {
        RelationEntity entity = new RelationEntity();
        entity.setCategory(category);
        return repository.findAll(Example.of(entity));
    }

    @Override
    public Collection<RelationEntity> findByCategoryAndName(String category, String name) {
        RelationEntity entity = new RelationEntity();
        entity.setCategory(category);
        entity.setName(name);
        return repository.findAll(Example.of(entity));
    }

    @Override
    public Collection<RelationEntity> findByStartNodeEntity(NodeEntity startNodeEntity) {
        return repository.findByStartNodeEntityId(startNodeEntity.getId());
    }

    @Override
    public Collection<RelationEntity> findByEndNodeEntity(NodeEntity endNodeEntity) {
        return repository.findByEndNodeEntityId(endNodeEntity.getId());
    }

    @Override
    public Collection<RelationEntity> findByStartNodeEntityAndCategory(NodeEntity startNodeEntity,
        String category) {
        return repository.findByStartNodeEntityIdAndCategory(startNodeEntity.getId(), category);
    }

    @Override
    public Collection<RelationEntity> findByStartNodeEntityAndCategoryAndName(NodeEntity startNodeEntity, String category, String name) {
        return repository.findByStartNodeEntityIdAndCategoryAndName(startNodeEntity.getId(), category,name);
    }

    @Override
    public Collection<RelationEntity> findByEndNodeEntityAndCategory(NodeEntity endNodeEntity,
        String category) {
        return repository.findByEndNodeEntityIdAndCategory(endNodeEntity.getId(), category);
    }

    @Override
    public Collection<RelationEntity> findByStartNodeEntityAndEndNodeEntity(
        NodeEntity startNodeEntity, NodeEntity endNodeEntity) {
        return repository.findByStartNodeEntityIdAndEndNodeEntityId(startNodeEntity.getId(), endNodeEntity.getId());
    }

    @Override
    public Collection<RelationEntity> findByStartNodeEntityAndEndNodeEntityAndCategory(
        NodeEntity startNodeEntity, NodeEntity endNodeEntity, String category) {
        return repository.findByStartNodeEntityIdAndEndNodeEntityIdAndCategory(startNodeEntity.getId(), endNodeEntity.getId(), category);
    }

    @Override
    public RelationEntity findByStartNodeEntityAndEndNodeEntityAndCategoryAndName(NodeEntity startNodeEntity, NodeEntity endNodeEntity, String category, String name) {
        return repository.findByStartNodeEntityIdAndEndNodeEntityIdAndCategoryAndName(startNodeEntity.getId(), endNodeEntity.getId(), category,name);

    }

    @Override
    public Page<RelationEntity> findByName(String name, Pageable pageable) {
        RelationEntity entity = new RelationEntity();
        entity.setName(name);
        return repository.findAll(Example.of(entity), pageable);
    }

    @Override
    public Page<RelationEntity> findByCategory(String category, Pageable pageable) {
        RelationEntity entity = new RelationEntity();
        entity.setCategory(category);
        return repository.findAll(Example.of(entity), pageable);
    }

    @Override
    public Page<RelationEntity> findByCategoryAndName(String category, String name,
        Pageable pageable) {
        RelationEntity entity = new RelationEntity();
        entity.setCategory(category);
        entity.setName(name);
        return repository.findAll(Example.of(entity), pageable);
    }

    @Override
    public Optional<RelationEntity> findOne(Specification<RelationEntity> spec) {
        return repository.findOne(spec);
    }

    @Override
    public List<RelationEntity> findAll(Specification<RelationEntity> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Page<RelationEntity> findAll(Specification<RelationEntity> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<RelationEntity> findAll(Specification<RelationEntity> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    @Override
    public long count(Specification<RelationEntity> spec) {
        return repository.count(spec);
    }
}
