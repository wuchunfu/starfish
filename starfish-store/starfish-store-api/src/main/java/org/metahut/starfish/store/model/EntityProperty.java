package org.metahut.starfish.store.model;

import java.io.Serializable;

public class EntityProperty<ID extends Serializable> {

    private ID id;

    private ID entityId;

    private String name;

    private Object value;
}
