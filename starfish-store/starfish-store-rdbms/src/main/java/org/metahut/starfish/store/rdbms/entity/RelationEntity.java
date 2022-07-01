package org.metahut.starfish.store.rdbms.entity;

import org.metahut.starfish.store.model.AbstractRelationEntity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Lazy;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "t_sf_relation_entity")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class RelationEntity extends
    AbstractRelationEntity<Long, RelationEntityProperty, NodeEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @OneToMany(targetEntity = RelationEntityProperty.class, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Lazy
    private Set<RelationEntityProperty> properties;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(targetEntity = NodeEntity.class, fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "start_node_entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private NodeEntity startNodeEntity;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(targetEntity = NodeEntity.class, fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "end_node_entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private NodeEntity endNodeEntity;

    @Column(name = "operator")
    private Integer operator;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public Map<String, RelationEntityProperty> getKeyedProperties() {
        return null;
    }

    @Override
    public void setKeyedProperties(Map<String, RelationEntityProperty> properties) {

    }
}
