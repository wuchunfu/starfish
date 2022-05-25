package org.metahut.starfish.service.expression;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class ConditionPiece {
    private TableType tableType;

    private List<BinaryExpression> expressions;

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
}
