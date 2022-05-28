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

import static org.metahut.starfish.api.Constants.COLLECTOR_TASK_TYPE_NAME;

@ApiModel(description = "collector task request dto")
public class CollectorTaskRequestDTO extends PageRequestDTO {

    @ApiModelProperty(value = "collector adapter name")
    private String adapterName;

    @ApiModelProperty(value = "collector adapter type")
    private String type;

    @ApiModelProperty(value = "collector task name")
    private String name;

    @ApiModelProperty(value = "update begin time")
    private Date updateBeginTime;

    @ApiModelProperty(value = "update end time")
    private Date updateEndTime;

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AbstractQueryCondition<CollectorTaskResponseDTO> toQueryCondition() {
        AbstractQueryCondition<CollectorTaskResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(CollectorTaskResponseDTO.class);
        condition.setFilters(Arrays.asList(collectorTaskPiece()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer eachPointer = new EachPointer();
        eachPointer.setCategory(TypeCategory.ENTITY);
        eachPointer.setRelationType(RelationType.CHILD);
        map.put("adapter",eachPointer);
        return map;
    }

    /**
     * ENTITY
     *      name
     * @return
     */
    private ConditionPiece collectorTaskPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        List<BinaryExpression> expressions = new ArrayList<>();
        Map<String,ConditionPiece> map = new HashMap<>();
        map.putAll(rel1());
        if (StringUtils.isNotEmpty(this.name)) {
            map.putAll(rel0());
        }
        if (StringUtils.isNotEmpty(adapterName) && StringUtils.isNotEmpty(type)) {
            map.putAll(rel3());
        }
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel0() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",propertyNamePiece());
        return result;
    }

    private ConditionPiece propertyNamePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueEqual("name",this.name))));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel1() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("parent",typeEntityRelationPiece());
        return result;
    }

    private ConditionPiece typeEntityRelationPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel2());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel2() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("startNodeEntity",collectorTaskTypePiece());
        return result;
    }

    private ConditionPiece collectorTaskTypePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.entity(COLLECTOR_TASK_TYPE_NAME));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel3() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("children",entityRel());
        return result;
    }

    private ConditionPiece entityRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"adapter"));
        conditionPiece.setNextConditionChain(rel4());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel4() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("endNodeEntity",collectorAdapterPiece());
        return result;
    }

    private ConditionPiece collectorAdapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        if (StringUtils.isNotEmpty(this.adapterName)) {
            conditionPiece.setExpressions(Expression.entity(adapterName));
        }
        if (StringUtils.isNotEmpty(this.type)) {
            conditionPiece.setNextConditionChain(rel5());
        }
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel5() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",propertyPiece());
        result.put("parent",adapterRelPiece());
        return result;
    }

    private ConditionPiece propertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueEqual("type",this.type))));
        return conditionPiece;
    }

    private ConditionPiece adapterRelPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel6());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel6() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("startNodeEntity",adapterPiece());
        return result;
    }

    private ConditionPiece adapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.entity("org.starfish.CollectorAdapter"));
        conditionPiece.setNextConditionChain(rel6());
        return conditionPiece;
    }
}
