package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.QueryHint;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RelationEntityRepository extends JpaRepository<RelationEntity, Long>,
    JpaSpecificationExecutor<RelationEntity> {

    List<RelationEntity> removeByName(String name);

    List<RelationEntity> removeByCategory(String category);

    List<RelationEntity> removeByStartNodeEntityAndEndNodeEntity(NodeEntity startNodeEntity, NodeEntity endNodeEntity);

    List<RelationEntity> removeByStartNodeEntityAndEndNodeEntityAndCategory(NodeEntity startNodeEntity, NodeEntity endNodeEntity, String category);

    List<RelationEntity> removeByStartNodeEntity(NodeEntity startNodeEntity);

    List<RelationEntity> removeByEndNodeEntity(NodeEntity endNodeEntity);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByStartNodeEntityId(Long startNodeEntityId);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByStartNodeEntityIdAndCategory(Long startNodeEntityId, String category);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByStartNodeEntityIdAndCategoryAndName(Long startNodeEntityId, String category,String name);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByEndNodeEntityId(Long endNodeEntityId);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByEndNodeEntityIdAndCategory(Long endNodeEntityId, String category);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByStartNodeEntityIdAndEndNodeEntityId(Long startNodeEntityId, Long endNodeEntityId);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<RelationEntity> findByStartNodeEntityIdAndEndNodeEntityIdAndCategory(Long startNodeEntityId, Long endNodeEntityId, String category);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    RelationEntity findByStartNodeEntityIdAndEndNodeEntityIdAndCategoryAndName(Long startNodeEntityId, Long endNodeEntityId, String category,String name);

    @Override
    List<RelationEntity> findAll();

    @Override
    List<RelationEntity> findAll(Sort sort);

    @Override
    List<RelationEntity> findAllById(Iterable<Long> longs);

    @Override
    <S extends RelationEntity> List<S> saveAll(Iterable<S> entities);

    @Override
    <S extends RelationEntity> S saveAndFlush(S entity);

    @Override
    <S extends RelationEntity> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<RelationEntity> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    RelationEntity getById(Long aLong);

    @Override
    <S extends RelationEntity> List<S> findAll(Example<S> example);

    @Override
    <S extends RelationEntity> List<S> findAll(Example<S> example, Sort sort);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<RelationEntity> findOne(Specification<RelationEntity> spec);

    @Override
    List<RelationEntity> findAll(Specification<RelationEntity> spec);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<RelationEntity> findAll(Specification<RelationEntity> spec, Pageable pageable);

    @Override
    List<RelationEntity> findAll(Specification<RelationEntity> spec, Sort sort);

    @Override
    long count(Specification<RelationEntity> spec);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<RelationEntity> findAll(Pageable pageable);

    @Override
    <S extends RelationEntity> S save(S entity);

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<RelationEntity> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(RelationEntity entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends RelationEntity> entities);

    @Override
    void deleteAll();

    @Override
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    <S extends RelationEntity> Optional<S> findOne(Example<S> example);

    @Override
    <S extends RelationEntity> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends RelationEntity> long count(Example<S> example);

    @Override
    <S extends RelationEntity> boolean exists(Example<S> example);

    @Override
    <S extends RelationEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
