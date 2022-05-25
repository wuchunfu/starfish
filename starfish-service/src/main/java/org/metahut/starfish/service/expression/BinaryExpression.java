package org.metahut.starfish.service.expression;

public abstract class BinaryExpression {
    private Expression leftExpression;
    private Expression rightExpression;
    public abstract String getStringExpression();

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(Expression leftExpression) {
        this.leftExpression = leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(Expression rightExpression) {
        this.rightExpression = rightExpression;
    }
}
