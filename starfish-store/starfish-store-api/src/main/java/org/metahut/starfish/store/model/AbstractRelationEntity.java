package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public abstract class AbstractRelationEntity<I extends Serializable, P extends AbstractEntityProperty, E extends AbstractNodeEntity> implements Serializable {

    public abstract I getId();

    public abstract void setId(I id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getCategory();

    public abstract void setCategory(String category);

    public abstract E getStartNodeEntity();

    public abstract void setStartNodeEntity(E nodeEntity);

    public abstract E getEndNodeEntity();

    public abstract void setEndNodeEntity(E nodeEntity);

    public abstract Map<String, P> getKeyedProperties();

    public abstract void setKeyedProperties(Map<String, P> properties);

    public abstract Integer getOperator();

    public abstract void setOperator(Integer operator);

    public abstract Date getCreateTime();

    public abstract void setCreateTime(Date createTime);

    public abstract Date getUpdateTime();

    public abstract void setUpdateTime(Date updateTime);

}
