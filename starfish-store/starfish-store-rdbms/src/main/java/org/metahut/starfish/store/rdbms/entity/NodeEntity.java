package org.metahut.starfish.store.rdbms.entity;

import org.metahut.starfish.store.model.AbstractNodeEntity;

import com.google.common.base.Joiner;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "t_sf_node_entity")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class NodeEntity extends AbstractNodeEntity<Long, NodeEntityProperty,NodeEntity, RelationEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qualifiedName", nullable = false)
    private String qualifiedName;

    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(targetEntity = NodeEntityProperty.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<NodeEntityProperty> properties;

    @Column(name = "operator")
    private Integer operator;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_node_entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),insertable = false,updatable = false)
    private List<RelationEntity> children;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_node_entity_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),insertable = false,updatable = false)
    private List<RelationEntity> parent;

    @Override
    public Map<String, NodeEntityProperty> getKeyedProperties() {
        return null;
    }

    @Override
    public void setKeyedProperties(Map<String, NodeEntityProperty> properties) {

    }

    @Override
    public String toString() {
        return "NodeEntity("
            + "id=" + id
            + ",qualifiedName=" + qualifiedName
            + ",category=" + category
            + ",properties=" + Joiner.on(",").join(properties)
            + ",operator=" + operator
            + ",createTime=" + createTime
            + ",updateTime=" + updateTime
            + ")";
    }
}
