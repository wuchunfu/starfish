package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractEntityProperty<ID extends Serializable, T, E> implements Serializable {

    public abstract ID getId();

    public abstract void setId(ID id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract T getValue();

    public abstract void setValue(T value);

    public abstract E getEntity();

    public abstract void setEntity(E entity);

    public abstract Integer getOperator();

    public abstract void setOperator(Integer operator);

    public abstract Date getCreateTime();

    public abstract void setCreateTime(Date createTime);

    public abstract Date getUpdateTime();

    public abstract void setUpdateTime(Date updateTime);


}
