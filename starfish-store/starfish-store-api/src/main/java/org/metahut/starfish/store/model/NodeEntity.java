package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.List;

public class NodeEntity<ID extends Serializable, T> {

    private ID id;

    private List<String> categories;

    private List<EntityProperty<ID, T>> properties;
}
