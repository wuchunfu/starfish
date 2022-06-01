package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface NodeEntityPropertyRepository extends JpaRepository<NodeEntityProperty, Long>,
    JpaSpecificationExecutor<NodeEntityProperty> {

    List<NodeEntityProperty> removeByName(String name);

    @Override
    List<NodeEntityProperty> findAll();

    @Override
    List<NodeEntityProperty> findAll(Sort sort);

    @Override
    List<NodeEntityProperty> findAllById(Iterable<Long> longs);

    @Override
    <S extends NodeEntityProperty> List<S> saveAll(Iterable<S> entities);

    @Override
    void flush();

    @Override
    <S extends NodeEntityProperty> S saveAndFlush(S entity);

    @Override
    <S extends NodeEntityProperty> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<NodeEntityProperty> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    NodeEntityProperty getById(Long aLong);

    @Override
    <S extends NodeEntityProperty> List<S> findAll(Example<S> example);

    @Override
    <S extends NodeEntityProperty> List<S> findAll(Example<S> example, Sort sort);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<NodeEntityProperty> findOne(Specification<NodeEntityProperty> spec);

    @Override
    List<NodeEntityProperty> findAll(Specification<NodeEntityProperty> spec);

    @Override
    Page<NodeEntityProperty> findAll(Specification<NodeEntityProperty> spec, Pageable pageable);

    @Override
    List<NodeEntityProperty> findAll(Specification<NodeEntityProperty> spec, Sort sort);

    @Override
    long count(Specification<NodeEntityProperty> spec);

    @Override
    Page<NodeEntityProperty> findAll(Pageable pageable);

    @Override
    <S extends NodeEntityProperty> S save(S entity);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<NodeEntityProperty> findById(Long aLong);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(NodeEntityProperty entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends NodeEntityProperty> entities);

    @Override
    void deleteAll();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntityProperty> Optional<S> findOne(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntityProperty> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntityProperty> long count(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntityProperty> boolean exists(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntityProperty, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
