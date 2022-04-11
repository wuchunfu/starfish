package org.metahut.starfish.store.dao;

import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEntityPropertyMapper<I extends Serializable, N extends AbstractNodeEntity, P extends AbstractEntityProperty> {

    /**
     *
     * @param property
     * @return the instance of affected records
     */
    P create(P property);

    /**
     *
     * @param properties
     * @return the instance of affected records
     */
    Collection<P> createBatch(Collection<P> properties);


    /**
     * @param property
     */
    void remove(P property);

    /**
     * @param properties
     */
    void removeBatch(Collection<P> properties);

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
     * @param property
     */
    void removeAllByName(P property);

    /**
     * @param property
     * @return the instance of updated property
     */
    P update(P property);

    /**
     * @param properties
     * @return the list of the instance of updated property
     */
    Collection<P> updateBatchById(Collection<P> properties);

    /**
     *
     * @param id
     * @return the instance of a property
     */
    P findById(I id);

    /**
     *
     * @param property
     * @return the instance of a property
     */
    P findById(P property);

    /**
     *
     * @param ids
     * @return the collection of properties
     */
    Collection<P> findAllById(Collection<Long> ids);

    /**
     *
     * @param name
     * @return the collection of the instance of P
     */
    Collection<P> findByName(String name);

    /**
     *
     * @param name
     * @param pageable
     * @return the page of the instance of N
     */
    Page<P> findByName(String name, Pageable pageable);
    
}
