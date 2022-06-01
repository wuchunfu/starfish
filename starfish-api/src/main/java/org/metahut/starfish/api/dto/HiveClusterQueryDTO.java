package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.Constants;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
@ApiModel(description = "hive cluster query dto")
public class HiveClusterQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "hive cluster name")
    private String clusterName;

    @ApiModelProperty(value = "hive cluster description")
    private String description;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbstractQueryCondition<HiveClusterResponseDTO> toCondition() {
        AbstractQueryCondition<HiveClusterResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(HiveClusterResponseDTO.class);
        condition.setFilters(Arrays.asList(typePiece()));
        return condition;
    }

    private ConditionPiece typePiece() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithType(Constants.HIVE_CLUSTER_TYPE_NAME);
        if (!StringUtils.isAllEmpty(this.clusterName,this.description)) {
            conditionPiece.getNextConditionChain().put(Expression.PROPERTIES, Arrays.asList(propertyCondition()));
        }
        return conditionPiece;
    }

    private ConditionPiece propertyCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList<>());
        if (StringUtils.isNotEmpty(this.clusterName)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("name",this.clusterName)));
        }
        if (StringUtils.isNotEmpty(this.description)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("description",this.description)));
        }
        return propertyCondition();
    }
}
