package org.metahut.starfish.store.rdbms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Table(name = "t_sf_node_entity")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class NodeEntity extends AbstractNodeEntity<Long, NodeEntityProperty> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> categories;

    @OneToMany(targetEntity = NodeEntityProperty.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "entity_id")
    @JsonIgnore
    private Set<NodeEntityProperty> properties;

    @Column(name = "operator")
    private Integer operator;

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public Map<String, NodeEntityProperty> getKeyedProperties() {
        return null;
    }

    @Override
    public void setKeyedProperties(Map<String, NodeEntityProperty> properties) {

    }
}
