package org.metahut.starfish.store.dao;

import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;

import java.io.Serializable;
import java.util.Collection;

public interface IEntityPropertyMapper<I extends Serializable, N extends AbstractNodeEntity, P extends AbstractEntityProperty> {

    /**
     *
     * @param entityId
     * @param property
     * @return the instance of a node entity with all properties
     */
    N updatePropertyByEntityId(I entityId, P property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    N updatePropertyByEntityId(N entity, P property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    N updatePropertiesByEntityId(N entity, Collection<P> property);

    /**
     *
     * @param entity
     * @param property
     */
    void removePropertyByEntityId(N entity, Collection<P> property);

    /**
     *
     * @param entityId
     * @param property
     */
    void removePropertyByEntityId(I entityId, Collection<P> property);

    /**
     *
     * @param entityId
     * @param property
     */
    void removeAllPropertiesByEntityId(I entityId, Collection<P> property);

    /**
     *
     * @param entity
     * @param property
     */
    void removeAllPropertiesByEntityId(N entity, Collection<P> property);
}
