package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(description = "collector adapter request dto")
public class CollectorAdapterRequestDTO extends PageRequestDTO {

    @ApiModelProperty(value = "collector adapter name")
    private String name;

    @ApiModelProperty(value = "collector adapter parameter to connect")
    private String parameter;

    @ApiModelProperty(value = "collector adapter description")
    private String description;

    @ApiModelProperty(value = "collector adapter type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AbstractQueryCondition<CollectorAdapterResponseDTO> toQueryCondition() {
        AbstractQueryCondition<CollectorAdapterResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(CollectorAdapterResponseDTO.class);
        condition.setFilters(Arrays.asList(collectorAdapterPiece()));
        return condition;
    }

    private ConditionPiece collectorAdapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        List<BinaryExpression> expressions = new ArrayList<>();
        Map<String, ConditionPiece> map = new HashMap<>();
        map.putAll(rel1());
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String, ConditionPiece> rel1() {
        Map<String, ConditionPiece> result = new HashMap<>();
        result.put("parent", typeEntityRelationPiece());
        result.put("properties", propertyPiece());
        return result;
    }

    private ConditionPiece typeEntityRelationPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY, LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel2());
        return conditionPiece;
    }

    private Map<String, ConditionPiece> rel2() {
        Map<String, ConditionPiece> result = new HashMap<>();
        result.put("startNodeEntity", collectorTaskTypePiece());
        return result;
    }

    private ConditionPiece collectorTaskTypePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.entity("org.starfish.CollectorAdapter"));
        return conditionPiece;
    }

    private ConditionPiece propertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList());
        if (StringUtils.isNotBlank(this.name)) {
            conditionPiece.getExpressions().addAll(Expression.keyValueLike("name", this.name));
        }
        if (StringUtils.isNotBlank(this.type)) {
            conditionPiece.getExpressions().addAll(Expression.keyValueEqual("type", this.type));
        }
        if (StringUtils.isNotBlank(this.description)) {
            conditionPiece.getExpressions().addAll(Expression.keyValueLike("description", this.description));
        }
        return conditionPiece;
    }

}
