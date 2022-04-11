package org.metahut.starfish.store.rdbms.entity;

import org.metahut.starfish.store.model.AbstractEntityProperty;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.AbstractMap;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "t_sf_relation_entity_property")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonType.class)
public class RelationEntityProperty extends AbstractEntityProperty<Long, Object, RelationEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = RelationEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private RelationEntity entity;

    @Column(name = "name", nullable = false)
    private String name;

    @Type(type = "json")
    @Column(columnDefinition = "json")
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
        this.value = new AbstractMap.SimpleEntry(name, value);
    }

    @Override
    public String toString() {
        return "RelationEntityProperty("
            + "id=" + id
            + ",name=" + name
            + ",entityId=" + entity.getId()
            + ",value=" + value
            + ",operator=" + operator
            + ",createTime=" + createTime
            + ",updateTime=" + updateTime
            + ")";
    }
}
