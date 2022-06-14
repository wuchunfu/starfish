package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.TableType;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Map<String,List<ConditionPiece>> nextConditionChain;

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

    public Map<String, List<ConditionPiece>> getNextConditionChain() {
        return nextConditionChain;
    }

    public void setNextConditionChain(Map<String, List<ConditionPiece>> nextConditionChain) {
        this.nextConditionChain = nextConditionChain;
    }

    public static ConditionPiece entityWithType(String typeName) {
        return entityWithTypeAndQualifiedName(typeName,null);
    }

    public static ConditionPiece entityWithTypeAndQualifiedName(String typeName,String qualifiedName) {
        return entityWithTypeAndIdAndQualifiedName(typeName,null,qualifiedName);
    }

    public static ConditionPiece entityWithIdAndQualifiedName(Long id,String qualifiedName) {
        return entityWithTypeAndIdAndQualifiedName(null,id,qualifiedName);
    }

    public static ConditionPiece entityWithTypeAndIdAndQualifiedName(String typeName,Long id,String qualifiedName) {
        ConditionPiece conditionPiece = new ConditionPiece();
        List<BinaryExpression> expressions = new ArrayList<>();
        conditionPiece.setExpressions(expressions);
        expressions.addAll(Expression.entity(id,qualifiedName));
        conditionPiece.setTableType(TableType.ENTITY);
        if (typeName != null && !"".equals(typeName)) {
            Map<String, List<ConditionPiece>> map = new HashMap<>();
            conditionPiece.setNextConditionChain(map);
            ConditionPiece conditionParent = new ConditionPiece();
            conditionParent.setTableType(TableType.RELATION);
            conditionParent.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY, LinkCategory.TYPE_ENTITY.name()));
            Map<String, List<ConditionPiece>> next = new HashMap<>();
            ConditionPiece typeCondition = new ConditionPiece();
            typeCondition.setTableType(TableType.ENTITY);
            typeCondition.setExpressions(Expression.type(typeName));
            next.put(Expression.START_NODE_ENTITY, Arrays.asList(typeCondition));
            conditionParent.setNextConditionChain(next);
            map.put(Expression.PARENT, Arrays.asList(conditionParent));
        }
        conditionPiece.setExpressions(expressions);
        return conditionPiece;
    }

}
