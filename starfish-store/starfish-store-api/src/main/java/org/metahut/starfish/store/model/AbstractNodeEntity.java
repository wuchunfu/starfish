package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public abstract class AbstractNodeEntity<ID extends Serializable, P extends AbstractEntityProperty> implements Serializable {

    public abstract ID getId();

    public abstract void setId(ID id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Set<String> getCategories();

    public abstract void setCategories(Set<String> categories);

    public abstract Map<String, P> getKeyedProperties();

    public abstract void setKeyedProperties(Map<String, P> properties);

    public abstract Integer getOperator();

    public abstract void setOperator(Integer operator);

    public abstract Date getCreateTime();

    public abstract void setCreateTime(Date createTime);

    public abstract Date getUpdateTime();

    public abstract void setUpdateTime(Date updateTime);
}
