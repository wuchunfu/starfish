package org.metahut.starfish.store.dao;

import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.metahut.starfish.store.model.AbstractRelationEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;

public interface IRelationEntityMapper<I extends Serializable, R extends AbstractRelationEntity, P extends AbstractEntityProperty,  N extends AbstractNodeEntity>  {

    /**
     *
     * @param relation
     * @return the instance of affected records
     */
    R create(R relation);

    /**
     *
     * @param relations
     * @return the instance of affected records
     */
    Collection<R> createBatch(Collection<R> relations);


    /**
     * @param relation
     */
    void remove(R relation);

    /**
     * @param relations
     */
    void removeBatch(Collection<R> relations);

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
     * @param relation
     */
    void removeAllByName(R relation);

    /**
     *
     * @param category
     */
    void removeAllByCategory(String category);

    /**
     *
     * @param relation
     */
    void removeAllByCategory(R relation);

    /**
     *
     * @param nodeEntity
     */
    void removeAllByNodeEntity(N nodeEntity);

    /**
     *
     * @param startNodeEntity
     * @param endNodeEntity
     */
    void removeAllByStartNodeEntityAndEndNodeEntity(N startNodeEntity, N endNodeEntity);

    /**
     * @param relation
     * @return the instance of updated entity
     */
    R update(R relation);

    /**
     * @param relations
     * @return the list of the instance of updated entity
     */
    Collection<R> updateBatchById(Collection<R> relations);

    /**
     *
     * @param id
     * @return the instance of a node entity with all properties
     */
    R findById(I id);

    /**
     *
     * @param relation
     * @return the instance of a node entity with all properties
     */
    R findById(R relation);

    /**
     *
     * @param ids
     * @return the collection of the instance of R
     */
    Collection<R> findAllById(Collection<Long> ids);

    /**
     *
     * @param name
     * @return the collection of the instance of R
     */
    Collection<R> findByName(String name);

    /**
     *
     * @param category
     * @return the collection of the instance of R
     */
    Collection<R> findByCategory(String category);

    /**
     *
     * @param category
     * @param name
     * @return the collection of the instance of R
     */
    Collection<R> findByCategoryAndName(String category, String name);

    /**
     *
     * @param startNodeEntity
     * @return the collection of the instance of R
     */
    Collection<R> findByStartNodeEntity(N startNodeEntity);

    /**
     *
     * @param endNodeEntity
     * @return the collection of the instance of R
     */
    Collection<R> findByEndNodeEntity(N endNodeEntity);

    /**
     *
     * @param startNodeEntity
     * @param endNodeEntity
     * @return the collection of the instance of R
     */
    Collection<R> findByStartNodeEntityAndEndNodeEntity(N startNodeEntity, N endNodeEntity);

    /**
     *
     * @param startNodeEntity
     * @param endNodeEntity
     * @param category
     * @return the collection of the instance of R
     */
    Collection<R> findByStartNodeEntityAndEndNodeEntityAndCategory(N startNodeEntity, N endNodeEntity, String category);

    /**
     *
     * @param name
     * @param pageable
     * @return the page of the instance of R
     */
    Page<R> findByName(String name, Pageable pageable);

    /**
     *
     * @param category
     * @param pageable
     * @return the page of the instance of R
     */
    Page<R> findByCategory(String category, Pageable pageable);

    /**
     *
     * @param category
     * @param name
     * @param pageable
     * @return the page of the instance of R
     */
    Page<R> findByCategoryAndName(String category, String name, Pageable pageable);

}
