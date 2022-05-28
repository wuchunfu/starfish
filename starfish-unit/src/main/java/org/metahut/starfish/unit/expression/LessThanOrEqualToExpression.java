package org.metahut.starfish.unit.expression;

/**
 *
 */
public class LessThanOrEqualToExpression<T extends Comparable> extends BinaryExpression<StringExpression,AbstractValueExpression<T>> {

    @Override
    public AbstractValueExpression<T> getRightExpression() {
        return super.getRightExpression();
    }

    @Override
    public void setRightExpression(AbstractValueExpression<T> rightExpression) {
        super.setRightExpression(rightExpression);
    }

    @Override
    public String getStringExpression() {
        return "le";
    }
}
