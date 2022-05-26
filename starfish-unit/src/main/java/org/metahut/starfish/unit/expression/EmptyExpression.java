package org.metahut.starfish.unit.expression;


/**
 *
 */
public class EmptyExpression extends BinaryExpression<StringExpression,StringExpression> {
    private boolean not = false;
    private Expression escapeExpression = null;
    private boolean caseInsensitive = false;

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    @Override
    public String getStringExpression() {
        return not ? "is not empty" : "is empty";
    }
}
