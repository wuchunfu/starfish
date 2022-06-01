package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;

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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface NodeEntityRepository extends JpaRepository<NodeEntity, Long>,
    JpaSpecificationExecutor<NodeEntity> {

    List<NodeEntity> removeByQualifiedName(String qualifiedName);

    List<NodeEntity> removeByCategory(String category);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<NodeEntity> findALlByIdIn(Collection<Long> ids, Pageable pageable);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntity> Optional<S> findOne(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<NodeEntity> findAllById(Iterable<Long> longs);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    NodeEntity getById(Long aLong);

    @Override
    <S extends NodeEntity> List<S> findAll(Example<S> example);

    @Override
    <S extends NodeEntity> List<S> findAll(Example<S> example, Sort sort);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<NodeEntity> findOne(Specification<NodeEntity> spec);

    @Override
    List<NodeEntity> findAll(Specification<NodeEntity> spec);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<NodeEntity> findAll(Specification<NodeEntity> spec, Pageable pageable);

    @Override
    List<NodeEntity> findAll(Specification<NodeEntity> spec, Sort sort);

    @Override
    long count(Specification<NodeEntity> spec);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<NodeEntity> findAll(Pageable pageable);

    @Override
    <S extends NodeEntity> S save(S entity);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<NodeEntity> findById(Long aLong);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(NodeEntity entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends NodeEntity> entities);

    @Override
    void deleteAll();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntity> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntity> long count(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntity> boolean exists(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends NodeEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
