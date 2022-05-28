package org.metahut.starfish.unit.expression;

/**
 *
 */
public abstract class LogicExpression extends BinaryExpression<BinaryExpression,BinaryExpressions> {

    @Override
    public BinaryExpressions getRightExpression() {
        return super.getRightExpression();
    }

    @Override
    public void setRightExpression(BinaryExpressions rightExpression) {
        super.setRightExpression(rightExpression);
    }
}
