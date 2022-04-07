package org.metahut.starfish.store.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


    Collection<N> findAllById(Collection<Long> ids);

    public List<N> findByName(String name);

    public List<N> findByCategory(String category);

    public List<N> findByCategoryAndName(String category, String name);

    public Page<N> findByName(String name, Pageable pageable);

    public Page<N> findByCategory(String category, Pageable pageable);

    public Page<N> findByCategoryAndName(String category, String name, Pageable pageable);


//    List<N> findByProperties(QueryConditionWrapper<N> wrapper);


    /**
     *
     * @param category
     * @param propertyName
     * @param propertyValue
     * @return
     */
//    List<N> findByProperty(String category, String propertyName, String propertyValue);

}
