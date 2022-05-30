package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ConditionPiece {
    private TableType tableType;

    private List<BinaryExpression> expressions;

    /**
     * String : property
     * join ->  join
     */
    private Map<String,ConditionPiece> nextConditionChain;

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public List<BinaryExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<BinaryExpression> expressions) {
        this.expressions = expressions;
    }

    public Map<String, ConditionPiece> getNextConditionChain() {
        return nextConditionChain;
    }

    public void setNextConditionChain(Map<String, ConditionPiece> nextConditionChain) {
        this.nextConditionChain = nextConditionChain;
    }

    public static ConditionPiece entityWithType(String typeName) {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        Map<String,ConditionPiece> map = new HashMap<>();
        conditionPiece.setNextConditionChain(map);
        ConditionPiece conditionParent = new ConditionPiece();
        conditionParent.setTableType(TableType.RELATION);
        conditionParent.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        Map<String,ConditionPiece> next = new HashMap<>();
        ConditionPiece typeCondition = new ConditionPiece();
        typeCondition.setTableType(TableType.ENTITY);
        typeCondition.setExpressions(Expression.type(typeName));
        next.put("startNodeEntity",typeCondition);
        conditionParent.setNextConditionChain(next);
        map.put("parent",conditionParent);
        return conditionPiece;
    }

    public static ConditionPiece entityWithTypeAndQualifiedName(String typeName,Long id,String qualifiedName) {
        ConditionPiece conditionPiece = entityWithType(typeName);
        List<BinaryExpression> expressions = new ArrayList<>();
        if (id != null) {
            expressions.add(Expression.id(id));
        }
        if (qualifiedName != null && !"".equals(qualifiedName)) {
            expressions.add(Expression.qualifiedName(qualifiedName));
        }
        conditionPiece.setExpressions(expressions);
        return conditionPiece;
    }

}
