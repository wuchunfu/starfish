package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.Category;
import org.metahut.starfish.unit.enums.TypeCategory;

import java.util.ArrayList;
import java.util.List;

public interface Expression {

    String CATEGORY = "category";

    String QUALIFIED_NAME = "qualifiedName";

    String NAME = "name";

    String VALUE = "value";

    static List<BinaryExpression> entity() {
        return entity(null);
    }

    static List<BinaryExpression> entity(String name) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression equalExpression = new EqualExpression();
        equalExpression.setLeftExpression(new StringExpression(CATEGORY));
        equalExpression.setRightExpression(new StringExpression(TypeCategory.ENTITY.name()));
        if (name != null && !"".equals(name)) {
            EqualExpression nameExpression = new EqualExpression();
            nameExpression.setLeftExpression(new StringExpression(QUALIFIED_NAME));
            nameExpression.setRightExpression(new StringExpression(name));
            result.add(nameExpression);
        }
        result.add(equalExpression);
        return result;
    }

    static List<BinaryExpression> keyValue(String key,String value) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        EqualExpression valueExpression = new EqualExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        valueExpression.setRightExpression(new StringExpression(value));
        result.add(valueExpression);
        result.add(keyExpression);
        return result;
    }

    static List<BinaryExpression> rel(Category category,String name) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression categoryExpression = new EqualExpression();
        categoryExpression.setLeftExpression(new StringExpression(CATEGORY));
        categoryExpression.setRightExpression(new StringExpression(category.name()));
        EqualExpression nameExpression = new EqualExpression();
        nameExpression.setLeftExpression(new StringExpression(NAME));
        nameExpression.setRightExpression(new StringExpression(name));
        result.add(categoryExpression);
        result.add(nameExpression);
        return result;
    }

    @Override
    String toString();

}
