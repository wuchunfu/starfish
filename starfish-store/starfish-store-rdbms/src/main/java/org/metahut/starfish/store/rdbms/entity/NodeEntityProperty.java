package org.metahut.starfish.store.rdbms.entity;

import org.metahut.starfish.store.model.AbstractEntityProperty;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name = "t_sf_node_entity_property")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonType.class)
public class NodeEntityProperty extends AbstractEntityProperty<Long, Object, NodeEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(targetEntity = NodeEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private NodeEntity entity;

    @Column(name = "name", nullable = false)
    private String name;

    @Type(type = "json")
    @Column(name = "property_value", columnDefinition = "json")
    private Object value;

    @Column(name = "operator")
    private Integer operator;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public void setValue(Object value) {
        Map<String, Object> wrappedMap = new LinkedHashMap<>();
        wrappedMap.put(name, value);
        this.value = wrappedMap;
    }

    @Override
    public Object getValue() {
        if (this.value instanceof Map) {
            return ((Map<?, ?>) this.value).get(name);
        }
        return this.value;
    }

    @Override
    public String toString() {
        return "NodeEntityProperty("
            + "id=" + id
            + ",name=" + name
            + ",value=" + value
            + ",operator=" + operator
            + ",createTime=" + createTime
            + ",updateTime=" + updateTime
            + ")";
    }
}
