package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.RelationEntityProperty;

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
public interface RelationEntityPropertyRepository extends JpaRepository<RelationEntityProperty, Long>,
    JpaSpecificationExecutor<RelationEntityProperty> {

    List<RelationEntityProperty> removeByName(String name);

    @Override
    List<RelationEntityProperty> findAll();

    @Override
    List<RelationEntityProperty> findAll(Sort sort);

    @Override
    List<RelationEntityProperty> findAllById(Iterable<Long> longs);

    @Override
    <S extends RelationEntityProperty> List<S> saveAll(Iterable<S> entities);

    @Override
    void flush();

    @Override
    <S extends RelationEntityProperty> S saveAndFlush(S entity);

    @Override
    <S extends RelationEntityProperty> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<RelationEntityProperty> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    RelationEntityProperty getById(Long aLong);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends RelationEntityProperty> List<S> findAll(Example<S> example);

    @Override
    <S extends RelationEntityProperty> List<S> findAll(Example<S> example, Sort sort);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<RelationEntityProperty> findOne(Specification<RelationEntityProperty> spec);

    @Override
    List<RelationEntityProperty> findAll(Specification<RelationEntityProperty> spec);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<RelationEntityProperty> findAll(Specification<RelationEntityProperty> spec, Pageable pageable);

    @Override
    List<RelationEntityProperty> findAll(Specification<RelationEntityProperty> spec, Sort sort);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    long count(Specification<RelationEntityProperty> spec);

    @Override
    Page<RelationEntityProperty> findAll(Pageable pageable);

    @Override
    <S extends RelationEntityProperty> S save(S entity);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<RelationEntityProperty> findById(Long aLong);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(RelationEntityProperty entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends RelationEntityProperty> entities);

    @Override
    void deleteAll();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends RelationEntityProperty> Optional<S> findOne(Example<S> example);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends RelationEntityProperty> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends RelationEntityProperty> long count(Example<S> example);

    @Override
    <S extends RelationEntityProperty> boolean exists(Example<S> example);

    @Override
    <S extends RelationEntityProperty, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
