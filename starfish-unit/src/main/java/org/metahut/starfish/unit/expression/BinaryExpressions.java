package org.metahut.starfish.unit.expression;

import java.util.List;

/**
 *
 */
public class BinaryExpressions implements Expression {
    private List<BinaryExpression> expressionList;

    public List<BinaryExpression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<BinaryExpression> expressionList) {
        this.expressionList = expressionList;
    }
}
