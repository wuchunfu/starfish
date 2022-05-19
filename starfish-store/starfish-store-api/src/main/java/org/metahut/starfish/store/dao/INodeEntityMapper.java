package org.metahut.starfish.store.dao;

import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface INodeEntityMapper<I extends Serializable, N extends AbstractNodeEntity, P extends AbstractEntityProperty> {

    /**
     *
     * @param entity
     * @return the instance of affected records
     */
    N create(N entity);

    /**
     *
     * @param entities
     * @return the instance of affected records
     */
    Collection<N> createBatch(Collection<N> entities);


    /**
     * @param entity
     */
    void remove(N entity);

    /**
     * @param entities
     */
    void removeBatch(Collection<N> entities);

    /**
     *
     */
    void removeAll();

    /**
     * @param ids
     */
    void removeBatchById(Collection<I> ids);

    /**
     *
     * @param name
     */
    void removeAllByName(String name);

    /**
     *
     * @param entity
     */
    void removeAllByName(N entity);

    /**
     *
     * @param category
     */
    void removeAllByCategory(String category);

    /**
     *
     * @param entity
     */
    void removeAllByCategory(N entity);

    /**
     * @param entity
     * @return the instance of updated entity
     */
    N update(N entity);

    /**
     * @param entities
     * @return the list of the instance of updated entity
     */
    Collection<N> updateBatchById(Collection<N> entities);

    /**
     *
     * @param id
     * @return the instance of a node entity with all properties
     */
    N findById(I id);

    /**
     *
     * @param entity
     * @return the instance of a node entity with all properties
     */
    N findById(N entity);

    /**
     *
     * @param ids
     * @return the collection of the instance of N
     */
    Collection<N> findAllById(Collection<Long> ids);

    /**
     *
     * @param name
     * @return the collection of the instance of N
     */
    Collection<N> findByName(String name);

    /**
     *
     * @param category
     * @return the collection of the instance of N
     */
    Collection<N> findByCategory(String category);

    /**
     *
     * @param category
     * @param name
     * @return the collection of the instance of N
     */
    Collection<N> findByCategoryAndName(String category, String name);

    /**
     *
     * @param name
     * @param pageable
     * @return the page of the instance of N
     */
    Page<N> findByName(String name, Pageable pageable);

    /**
     *
     * @param category
     * @param pageable
     * @return the page of the instance of N
     */
    Page<N> findByCategory(String category, Pageable pageable);

    /**
     *
     * @param category
     * @param name
     * @param pageable
     * @return the page of the instance of N
     */
    Page<N> findByCategoryAndName(String category, String name, Pageable pageable);

    /**
     *
     * @param spec complex querying conditions
     * @return the instance of N
     */
    Optional<N> findOne(Specification<N> spec);

    /**
     *
     * @param spec complex querying conditions
     * @return the list of instances of N
     */
    List<N> findAll(Specification<N> spec);

    /**
     *
     * @param spec complex querying conditions
     * @param pageable
     * @return the page of the instance of N
     */
    Page<N> findAll(Specification<N> spec, Pageable pageable);

    /**
     *
     * @param spec complex querying conditions
     * @param sort
     * @return the list of the instance of N
     */
    List<N> findAll(Specification<N> spec, Sort sort);

    /**
     *
     * @param spec complex querying conditions
     * @return the number of records matching the spec
     */
    long count(Specification<N> spec);
}
