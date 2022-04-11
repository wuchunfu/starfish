package org.metahut.starfish.store.rdbms.dao;

import java.util.Collection;
import org.metahut.starfish.store.dao.IEntityPropertyMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntityProperty;
import org.metahut.starfish.store.rdbms.repository.RelationEntityPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class RelationEntityPropertyMapper implements
    IEntityPropertyMapper<Long, NodeEntity, RelationEntityProperty> {

    @Autowired
    private RelationEntityPropertyRepository repository;

    @Override
    public RelationEntityProperty create(RelationEntityProperty property) {
        property.setId(null);
        return repository.save(property);
    }

    @Override
    public Collection<RelationEntityProperty> createBatch(Collection<RelationEntityProperty> properties) {
        properties.stream().forEach(property -> {
            property.setId(null);
        });
        return repository.saveAll(properties);
    }

    @Override
    public void remove(RelationEntityProperty property) {
        repository.delete(property);
    }

    @Override
    public void removeBatch(Collection<RelationEntityProperty> properties) {
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
    public void removeAllByName(RelationEntityProperty property) {
        repository.removeByName(property.getName());
    }

    @Override
    public RelationEntityProperty update(RelationEntityProperty property) {
        return repository.save(property);
    }

    @Override
    public Collection<RelationEntityProperty> updateBatchById(Collection<RelationEntityProperty> properties) {
        return repository.saveAll(properties);
    }

    @Override
    public RelationEntityProperty findById(Long id) {
        return repository.getById(id);
    }

    @Override
    public RelationEntityProperty findById(RelationEntityProperty property) {
        return findById(property.getId());
    }

    @Override
    public Collection<RelationEntityProperty> findAllById(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Collection<RelationEntityProperty> findByName(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");

        RelationEntityProperty property = new RelationEntityProperty();
        property.setName(name);

        return repository.findAll(Example.of(property, matcher));
    }

    @Override
    public Page<RelationEntityProperty> findByName(String name, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase("name");

        RelationEntityProperty property = new RelationEntityProperty();
        property.setName(name);

        return repository.findAll(Example.of(property, matcher), pageable);
    }
}
