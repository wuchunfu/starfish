package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.enums.TypeCategory;
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

import static org.metahut.starfish.api.Constants.HIVE_TABLE_TYPE_NAME;

/**
 *
 */
@ApiModel(description = "pulsar topic page query dto")
public class PulsarTopicQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "pulsar topic name")
    private String topicName;

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
        EachPointer topicParent = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.PARENT);
        map.put("topics",topicParent);
        EachPointer namespaceParent = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.PARENT);
        topicParent.addPointerChain("namespaces",namespaceParent);
        EachPointer tenantPointer = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.PARENT);
        namespaceParent.addPointerChain("tenant",tenantPointer);
        EachPointer clusterPointer = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.PARENT);
        namespaceParent.addPointerChain("allowedClusters",clusterPointer);
        EachPointer publisherChild = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.CHILD);
        map.put("publishers",publisherChild);
        return map;
    }

    private ConditionPiece pulsarTopicCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        List<BinaryExpression> expressions = new ArrayList<>();
        Map<String,ConditionPiece> map = new HashMap<>();
        map.putAll(pulsarTopicParentRelEnd());
        if (StringUtils.isNotEmpty(topicName)) {
            map.putAll(pulsarTopicPropRel());
        }
        if (StringUtils.isNotEmpty(publisherName) && StringUtils.isNotEmpty(publisherTeam)) {
            map.putAll(pulsarChildrenRelStart());
        }
        if (this.createBeginTime != null) {
            if (this.createEndTime != null) {
                expressions.add(Expression.dateBetweenAnd(Expression.CREATE_TIME,this.createBeginTime,this.createEndTime));
            } else {
                expressions.add(Expression.dateGreaterThanOrEqualTo(Expression.CREATE_TIME,this.createBeginTime));
            }
        } else {
            if (this.createEndTime != null) {
                expressions.add(Expression.dateLessThanOrEqualTo(Expression.CREATE_TIME,this.createEndTime));
            }
        }
        if (this.updateBeginTime != null) {
            if (this.updateEndTime != null) {
                expressions.add(Expression.dateBetweenAnd(Expression.UPDATE_TIME, this.updateBeginTime,this.updateEndTime));
            } else {
                expressions.add(Expression.dateGreaterThanOrEqualTo(Expression.UPDATE_TIME,this.updateBeginTime));
            }
        } else {
            if (this.updateEndTime != null) {
                expressions.add(Expression.dateLessThanOrEqualTo(Expression.UPDATE_TIME,this.updateEndTime));
            }
        }
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarTopicParentRelEnd() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("parent",pulsarTypeRel());
        return result;
    }

    private ConditionPiece pulsarTypeRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(pulsarTopicParentRelStart());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarTopicParentRelStart() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("startNodeEntity",pulsarTypeCondition());
        return result;
    }

    private ConditionPiece pulsarTypeCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.entity(HIVE_TABLE_TYPE_NAME));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarTopicPropRel() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",pulsarTopicPropCondition());
        return result;
    }

    private ConditionPiece pulsarTopicPropCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("name",this.topicName))));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarChildrenRelStart() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("children",pulsarPublisherRel());
        return result;
    }

    private ConditionPiece pulsarPublisherRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"publishers"));
        conditionPiece.setNextConditionChain(pulsarChildrenRelEnd());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarChildrenRelEnd() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("endNodeEntity",pulsarPublisherCondition());
        return result;
    }

    private ConditionPiece pulsarPublisherCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(pulsarPublisherPropRel());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> pulsarPublisherPropRel() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",pulsarPublisherPropCondition());
        return result;
    }

    private ConditionPiece pulsarPublisherPropCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        if (StringUtils.isNotEmpty(this.publisherName)) {
            conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("producerName", this.publisherName))));
        }
        if (StringUtils.isNotEmpty(this.publisherTeam)) {
            conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("description", this.publisherTeam))));
        }
        return conditionPiece;
    }

}
