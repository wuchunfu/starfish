package org.metahut.starfish.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbstractNodeEntity<I extends Serializable, P extends AbstractEntityProperty,E extends AbstractNodeEntity,R extends AbstractRelationEntity> implements Serializable {

    public abstract I getId();

    public abstract void setId(I id);

    public abstract String getQualifiedName();

    public abstract void setQualifiedName(String name);

    public abstract String getCategory();

    public abstract void setCategory(String category);

    public abstract Map<String, P> getKeyedProperties();

    public abstract void setKeyedProperties(Map<String, P> properties);

    public abstract Integer getOperator();

    public abstract void setOperator(Integer operator);

    public abstract Date getCreateTime();

    public abstract void setCreateTime(Date createTime);

    public abstract Date getUpdateTime();

    public abstract void setUpdateTime(Date updateTime);

    public abstract List<R> getChildren();

    public abstract List<R> getParent();
}
