package org.metahut.starfish.unit.expression;

public class LikeExpression extends BinaryExpression {
    private boolean not = false;
    private Expression escapeExpression = null;
    private boolean caseInsensitive = false;

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean b) {
        not = b;
    }

    @Override
    public String getStringExpression() {
        return caseInsensitive ? "ILIKE" : "LIKE";
    }

}
