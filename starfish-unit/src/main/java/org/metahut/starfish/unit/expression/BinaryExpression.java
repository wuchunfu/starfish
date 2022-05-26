package org.metahut.starfish.unit.expression;

public abstract class BinaryExpression<L extends Expression,R extends Expression> implements Expression {
    private L leftExpression;
    private R rightExpression;
    public abstract String getStringExpression();

    public L getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(L leftExpression) {
        this.leftExpression = leftExpression;
    }

    public R getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(R rightExpression) {
        this.rightExpression = rightExpression;
    }
}
