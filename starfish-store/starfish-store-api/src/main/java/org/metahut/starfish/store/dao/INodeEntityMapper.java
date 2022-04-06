package org.metahut.starfish.store.dao;

import java.util.Collection;
import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;

public interface INodeEntityMapper<N extends AbstractNodeEntity, P extends AbstractEntityProperty> {

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

}
