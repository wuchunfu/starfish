package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.List;

public class RelationEntity<ID extends Serializable, T> {

    private ID id;

    private String category;

    private ID startNodeId;

    private ID endNodeId;

    private List<EntityProperty<ID, T>> properties;
}
