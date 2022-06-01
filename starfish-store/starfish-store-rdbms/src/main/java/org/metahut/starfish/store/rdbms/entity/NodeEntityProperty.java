package org.metahut.starfish.store.rdbms.entity;

import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.rdbms.type.JsonStringTypeSupportString;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Cacheable;
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

@Setter
@Getter
@Entity
@Table(name = "t_sf_node_entity_property")
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@TypeDef(name = "json", typeClass = JsonStringTypeSupportString.class)
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
    @Column(name = "property_value")
    private Object value;

    @Column(name = "operator")
    private Integer operator;

    @CreatedDate
    @Column(name = "create_time", nullable = false,updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

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
