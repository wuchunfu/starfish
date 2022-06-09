package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.Category;
import org.metahut.starfish.unit.enums.TypeCategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface Expression {

    String CATEGORY = "category";

    String QUALIFIED_NAME = "qualifiedName";

    String NAME = "name";

    String VALUE = "value";

    String ID = "id";

    String UPDATE_TIME = "updateTime";

    String CREATE_TIME = "createTime";

    String PROPERTIES = "properties";

    String PARENT = "parent";

    String START_NODE_ENTITY = "startNodeEntity";

    String END_NODE_ENTITY = "endNodeEntity";

    String CHILDREN = "children";

    static List<BinaryExpression> entity() {
        return entity(null);
    }

    static List<BinaryExpression> source(String name) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression equalExpression = new EqualExpression();
        equalExpression.setLeftExpression(new StringExpression(CATEGORY));
        equalExpression.setRightExpression(new StringExpression(TypeCategory.SOURCE.name()));
        if (name != null && !"".equals(name)) {
            LikeExpression nameExpression = new LikeExpression();
            nameExpression.setLeftExpression(new StringExpression(QUALIFIED_NAME));
            nameExpression.setRightExpression(new StringExpression(name));
            result.add(nameExpression);
        }
        result.add(equalExpression);
        return result;
    }

    static List<BinaryExpression> entity(String name) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression equalExpression = new EqualExpression();
        equalExpression.setLeftExpression(new StringExpression(CATEGORY));
        equalExpression.setRightExpression(new StringExpression(TypeCategory.ENTITY.name()));
        if (name != null && !"".equals(name)) {
            result.add(Expression.qualifiedName(name));
        }
        result.add(equalExpression);
        return result;
    }

    static List<BinaryExpression> type(String name) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression equalExpression = new EqualExpression();
        equalExpression.setLeftExpression(new StringExpression(CATEGORY));
        equalExpression.setRightExpression(new StringExpression(TypeCategory.CLASSIFICATION.name()));
        if (name != null && !"".equals(name)) {
            EqualExpression nameExpression = new EqualExpression();
            nameExpression.setLeftExpression(new StringExpression(QUALIFIED_NAME));
            nameExpression.setRightExpression(new StringExpression(name));
            result.add(nameExpression);
        }
        result.add(equalExpression);
        return result;
    }

    static BinaryExpression equal(String property,String value) {
        EqualExpression expression = new EqualExpression();
        expression.setLeftExpression(new StringExpression(property));
        expression.setRightExpression(new StringExpression(value));
        return expression;
    }

    static BinaryExpression like(String property,String value) {
        LikeExpression expression = new LikeExpression();
        expression.setLeftExpression(new StringExpression(property));
        expression.setRightExpression(new StringExpression(value));
        return expression;
    }

    static BinaryExpression betweenAnd(String property,String begin,String end) {
        BetweenAndExpression expression = new BetweenAndExpression();
        expression.setLeftExpression(new StringExpression(property));
        StringPairExpression stringPairExpression = new StringPairExpression();
        expression.setRightExpression(stringPairExpression);
        stringPairExpression.setLeftExpression(new StringExpression(begin));
        stringPairExpression.setRightExpression(new StringExpression(end));
        return expression;
    }

    static BinaryExpression dateBetweenAnd(String property,Date begin,Date end) {
        DateBetweenAndExpression expression = new DateBetweenAndExpression();
        expression.setLeftExpression(new StringExpression(property));
        DatePairExpression datePairExpression = new DatePairExpression();
        expression.setRightExpression(datePairExpression);
        datePairExpression.setLeftExpression(new DateExpression(begin));
        datePairExpression.setRightExpression(new DateExpression(end));
        return expression;
    }

    static BinaryExpression dateGreaterThanOrEqualTo(String property,Date date) {
        GreaterThanOrEqualToExpression expression = new GreaterThanOrEqualToExpression();
        expression.setLeftExpression(new StringExpression(property));
        expression.setRightExpression(new DateExpression(date));
        return expression;
    }

    static BinaryExpression dateLessThanOrEqualTo(String property,Date date) {
        LessThanOrEqualToExpression expression = new LessThanOrEqualToExpression();
        expression.setLeftExpression(new StringExpression(property));
        expression.setRightExpression(new DateExpression(date));
        return expression;
    }

    static List<BinaryExpression> keyValueEqual(String key,String value) {
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

    static List<BinaryExpression> keyValueLike(String key,String value) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        LikeExpression valueExpression = new LikeExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        valueExpression.setRightExpression(new StringExpression(value));
        result.add(valueExpression);
        result.add(keyExpression);
        return result;
    }

    static BinaryExpression id(Long id) {
        EqualExpression idExpression = new EqualExpression();
        idExpression.setLeftExpression(new StringExpression(Expression.ID));
        idExpression.setRightExpression(new LongExpression(id));
        return idExpression;
    }

    static BinaryExpression qualifiedName(String qualifiedName) {
        EqualExpression qualifiedNameExpression = new EqualExpression();
        qualifiedNameExpression.setLeftExpression(new StringExpression(Expression.QUALIFIED_NAME));
        qualifiedNameExpression.setRightExpression(new StringExpression(qualifiedName));
        return qualifiedNameExpression;
    }

    static BinaryExpression and(List<BinaryExpression> expressions) {
        AndExpression andExpression = new AndExpression();
        andExpression.setLeftExpression(expressions.remove(0));
        andExpression.setRightExpression(new BinaryExpressions(expressions));
        return andExpression;
    }

    static List<BinaryExpression> keyValueDateBetweenAnd(String key,Date beginTime, Date endTime) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        DateBetweenAndExpression betweenAndExpression = new DateBetweenAndExpression();
        betweenAndExpression.setLeftExpression(new StringExpression(VALUE));
        DatePairExpression datePairExpression = new DatePairExpression();
        betweenAndExpression.setRightExpression(datePairExpression);
        datePairExpression.setLeftExpression(new DateExpression(beginTime));
        datePairExpression.setRightExpression(new DateExpression(endTime));
        result.add(keyExpression);
        result.add(betweenAndExpression);
        return result;
    }

    static List<BinaryExpression> keyValueDateGreaterThan(String key,Date date) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        GreaterThanExpression<Date> valueExpression = new GreaterThanExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        DateExpression dateExpression = new DateExpression(date);
        valueExpression.setRightExpression(dateExpression);
        result.add(keyExpression);
        result.add(valueExpression);
        return result;
    }

    static List<BinaryExpression> keyValueDateLessThan(String key,Date date) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        LessThanExpression<Date> valueExpression = new LessThanExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        DateExpression dateExpression = new DateExpression(date);
        valueExpression.setRightExpression(dateExpression);
        result.add(keyExpression);
        result.add(valueExpression);
        return result;
    }

    static List<BinaryExpression> keyValueDateGreaterThanOrEqualTo(String key,Date date) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        GreaterThanOrEqualToExpression<Date> valueExpression = new GreaterThanOrEqualToExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        DateExpression dateExpression = new DateExpression(date);
        valueExpression.setRightExpression(dateExpression);
        result.add(keyExpression);
        result.add(valueExpression);
        return result;
    }

    static List<BinaryExpression> keyValueDateLessThanOrEqualTo(String key,Date date) {
        List<BinaryExpression> result = new ArrayList<>();
        EqualExpression keyExpression = new EqualExpression();
        keyExpression.setLeftExpression(new StringExpression(NAME));
        keyExpression.setRightExpression(new StringExpression(key));
        LessThanOrEqualToExpression<Date> valueExpression = new LessThanOrEqualToExpression();
        valueExpression.setLeftExpression(new StringExpression(VALUE));
        DateExpression dateExpression = new DateExpression(date);
        valueExpression.setRightExpression(dateExpression);
        result.add(keyExpression);
        result.add(valueExpression);
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
