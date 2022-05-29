package org.metahut.starfish.ingestion.server.dto;

import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TypeCategory;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectorExecuteRequestDTO {

    private Long id;

    private String qualifiedName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public AbstractQueryCondition<CollectorTaskEntity> toCondition() {
        AbstractQueryCondition<CollectorTaskEntity> condition = new AbstractQueryCondition<>();
        condition.setResultType(CollectorTaskEntity.class);
        condition.setEachPointers(eachPointerMap());
        condition.setFilters(Arrays.asList(collectorTaskCondition()));
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer topicParent = new EachPointer(TypeCategory.ENTITY, RelationType.CHILD);
        map.put("adapter",topicParent);
        return map;
    }

    private ConditionPiece collectorTaskCondition() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithType("org.starfish.CollectorTask");
        List<BinaryExpression> expressions = new ArrayList<>();
        if (id != null) {
            expressions.add(Expression.id(id));
        }
        conditionPiece.setExpressions(expressions);
        return conditionPiece;
    }
}
