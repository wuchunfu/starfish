package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.metahut.starfish.api.Constants.PULSAR_TOPIC_TYPE_NAME;

/**
 *
 */
@ApiModel(description = "pulsar topic page query dto")
public class PulsarTopicQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "pulsar topic name")
    private String topicName;

    @ApiModelProperty(value = "pulsar cluster id")
    private Long clusterId;

    @ApiModelProperty(value = "pulsar cluster name")
    private String clusterName;

    @ApiModelProperty(value = "pulsar publisher name")
    private String publisherName;

    @ApiModelProperty(value = "pulsar publisher team name")
    private String publisherTeam;

    @ApiModelProperty(value = "pulsar topic create begin time")
    private Date createBeginTime;

    @ApiModelProperty(value = "pulsar topic create end time")
    private Date createEndTime;

    @ApiModelProperty(value = "pulsar topic update begin time")
    private Date updateBeginTime;

    @ApiModelProperty(value = "pulsar topic update end time")
    private Date updateEndTime;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherTeam() {
        return publisherTeam;
    }

    public void setPublisherTeam(String publisherTeam) {
        this.publisherTeam = publisherTeam;
    }

    public Date getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(Date createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(Date updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public Date getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Date updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public AbstractQueryCondition<PulsarTopicResponseDTO> toCondition() {
        AbstractQueryCondition<PulsarTopicResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(PulsarTopicResponseDTO.class);
        condition.setFilters(Arrays.asList(pulsarTopicCondition()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer namespaceParent = new EachPointer(LinkCategory.RELATIONSHIP,
            RelationType.CHILD);
        map.put("namespace", namespaceParent);
        EachPointer tenantPointer = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.CHILD);
        namespaceParent.addPointerChain("tenant", tenantPointer);
        EachPointer clusterPointer = new EachPointer(LinkCategory.RELATIONSHIP,
            RelationType.CHILD);
        tenantPointer.addPointerChain("allowedClusters", clusterPointer);
        EachPointer publisherChild = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.CHILD);
        map.put("publishers", publisherChild);
        return map;
    }

    private ConditionPiece pulsarTopicCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        List<BinaryExpression> expressions = new ArrayList<>();
        Map<String, List<ConditionPiece>> map = new HashMap<>();
        map.putAll(pulsarTopicParentRelEnd());
        if (StringUtils.isNotEmpty(topicName)) {
            map.putAll(pulsarTopicPropRel());
        }
        if (!StringUtils.isAllEmpty(publisherName, publisherTeam)) {
            map.putAll(pulsarChildrenRelStart());
        }
        if (this.createBeginTime != null) {
            if (this.createEndTime != null) {
                expressions.add(Expression
                    .dateBetweenAnd(Expression.CREATE_TIME, this.createBeginTime,
                        this.createEndTime));
            } else {
                expressions.add(Expression
                    .dateGreaterThanOrEqualTo(Expression.CREATE_TIME, this.createBeginTime));
            }
        } else {
            if (this.createEndTime != null) {
                expressions.add(
                    Expression.dateLessThanOrEqualTo(Expression.CREATE_TIME, this.createEndTime));
            }
        }
        if (this.updateBeginTime != null) {
            if (this.updateEndTime != null) {
                expressions.add(Expression
                    .dateBetweenAnd(Expression.UPDATE_TIME, this.updateBeginTime,
                        this.updateEndTime));
            } else {
                expressions.add(Expression
                    .dateGreaterThanOrEqualTo(Expression.UPDATE_TIME, this.updateBeginTime));
            }
        } else {
            if (this.updateEndTime != null) {
                expressions.add(
                    Expression.dateLessThanOrEqualTo(Expression.UPDATE_TIME, this.updateEndTime));
            }
        }
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarTopicParentRelEnd() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        List<ConditionPiece> conditionPieces = new ArrayList<>();
        conditionPieces.add(pulsarTypeRel());
        if (clusterId != null || StringUtils.isNotEmpty(clusterName)) {
            conditionPieces.add(pulsarNamespaceRel());
        }
        result.put("parent", conditionPieces);
        return result;
    }

    private ConditionPiece pulsarTypeRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(
            Expression.rel(LinkCategory.TYPE_ENTITY, LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(pulsarTopicParentRelStart());
        return conditionPiece;
    }

    private ConditionPiece pulsarNamespaceRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP, "topics"));
        conditionPiece.setNextConditionChain(pulsarNamespaceRelStart());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarNamespaceRelStart() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(pulsarNamespace()));
        return result;
    }

    private ConditionPiece pulsarNamespace() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(pulsarNamespaceRelEnd());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarNamespaceRelEnd() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PARENT, Arrays.asList(pulsarTenantRel()));
        return result;
    }

    private ConditionPiece pulsarTenantRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP, "namespaces"));
        conditionPiece.setNextConditionChain(pulsarTenantRelStart());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarTenantRelStart() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(pulsarTenant()));
        return result;
    }

    private ConditionPiece pulsarTenant() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        // if (clusterId != null) {
        //     conditionPiece.setExpressions(Arrays.asList(Expression.id(clusterId)));
        // }pulsarClusterProps()
        conditionPiece.setNextConditionChain(pulsarTenantRelEnd());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarTenantRelEnd() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PARENT, Arrays.asList(pulsarClusterRel()));
        return result;
    }

    private ConditionPiece pulsarClusterRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP, "allowedTenants"));
        conditionPiece.setNextConditionChain(pulsarClusterRelStart());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarClusterRelStart() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(pulsarCluster()));
        return result;
    }

    private ConditionPiece pulsarCluster() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        if (clusterId != null) {
            conditionPiece.setExpressions(Arrays.asList(Expression.id(clusterId)));
        }
        conditionPiece.setNextConditionChain(pulsarClusterProps());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarClusterProps() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES, Arrays.asList(pulsarClusterPropsCondition()));
        return result;
    }

    private ConditionPiece pulsarClusterPropsCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        if (StringUtils.isNotEmpty(this.clusterName)) {
            conditionPiece.setExpressions(
                Arrays.asList(Expression.and(Expression.keyValueLike("name", this.clusterName))));
        }
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarTopicParentRelStart() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(pulsarTypeCondition()));
        return result;
    }

    private ConditionPiece pulsarTypeCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.type(PULSAR_TOPIC_TYPE_NAME));
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarTopicPropRel() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES, Arrays.asList(pulsarTopicPropCondition()));
        return result;
    }

    private ConditionPiece pulsarTopicPropCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(
            Arrays.asList(Expression.and(Expression.keyValueLike("name", this.topicName))));
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarChildrenRelStart() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.CHILDREN, Arrays.asList(pulsarPublisherRel()));
        return result;
    }

    private ConditionPiece pulsarPublisherRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP, "publishers"));
        conditionPiece.setNextConditionChain(pulsarChildrenRelEnd());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarChildrenRelEnd() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.END_NODE_ENTITY, Arrays.asList(pulsarPublisherCondition()));
        return result;
    }

    private ConditionPiece pulsarPublisherCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(pulsarPublisherPropRel());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> pulsarPublisherPropRel() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES, Arrays.asList(pulsarPublisherPropCondition()));
        return result;
    }

    private ConditionPiece pulsarPublisherPropCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList<>());
        if (StringUtils.isNotEmpty(this.publisherName)) {
            conditionPiece.getExpressions()
                .add(Expression.and(Expression.keyValueLike("producerName", this.publisherName)));
        }
        if (StringUtils.isNotEmpty(this.publisherTeam)) {
            conditionPiece.getExpressions()
                .add(Expression.and(Expression.keyValueLike("description", this.publisherTeam)));
        }
        return conditionPiece;
    }

}
