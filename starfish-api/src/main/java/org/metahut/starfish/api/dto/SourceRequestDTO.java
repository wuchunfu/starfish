package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class SourceRequestDTO extends PageRequestDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractQueryCondition<SourceResponseDTO> toCondition() {
        AbstractQueryCondition<SourceResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(SourceResponseDTO.class);
        condition.setFilters(Arrays.asList(sourceType()));
        return condition;
    }

    private ConditionPiece sourceType() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(new ArrayList<>());
        conditionPiece.getExpressions().addAll(Expression.source(name));
        return conditionPiece;
    }

}
