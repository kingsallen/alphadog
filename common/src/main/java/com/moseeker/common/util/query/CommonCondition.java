package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class CommonCondition extends Condition {

    protected CommonCondition init(Condition condition) {
        this.innerCondition = condition.innerCondition;
        this.valueCondition = condition.valueCondition;
        return this;
    }

    public CommonCondition and(CommonCondition condition) {
        Condition condition1 = new Condition();
        condition1.innerCondition = this.innerCondition;
        condition1.valueCondition = this.valueCondition;
        InnerCondition innerCondition = new InnerCondition(condition1, condition, ConditionOp.AND);
        setInnerCondition(innerCondition);
        setValueCondition(null);
        return this;
    }

    public CommonCondition or(CommonCondition condition) {
        Condition condition1 = new Condition();
        condition1.innerCondition = this.innerCondition;
        condition1.valueCondition = this.valueCondition;
        InnerCondition innerCondition = new InnerCondition(condition1, condition, ConditionOp.OR);
        setInnerCondition(innerCondition);
        setValueCondition(null);
        return this;
    }

    static CommonCondition valueCondition(String field, Object value, ValueOp op) {
        ValueCondition valueCondition = new ValueCondition(field, String.valueOf(value), op);
        Condition c = new Condition();
        c.setValueCondition(valueCondition);
        return new CommonCondition().init(c);
    }

    public static CommonCondition equal(String field, Object value) {
        return valueCondition(field, value, ValueOp.EQ);
    }

    public static CommonCondition notEqual(String field, Object value) {
        return valueCondition(field, value, ValueOp.NEQ);
    }

    public static CommonCondition greater(String field, Object value) {
        return valueCondition(field, value, ValueOp.GT);
    }

    public static CommonCondition greaterOrEqual(String field, Object value) {
        return valueCondition(field, value, ValueOp.GE);
    }

    public static CommonCondition less(String field, Object value) {
        return valueCondition(field, value, ValueOp.LT);
    }

    public static CommonCondition lessOrEqual(String field, Object value) {
        return valueCondition(field, value, ValueOp.LE);
    }

    public static CommonCondition in(String field, Object value) {
        return valueCondition(field, value, ValueOp.IN);
    }

    public static CommonCondition notIn(String field, Object value) {
        return valueCondition(field, value, ValueOp.NIN);
    }

    public static CommonCondition between(String field, Object value) {
        return valueCondition(field, value, ValueOp.BT);
    }

    public static CommonCondition notBetween(String field, Object value) {
        return valueCondition(field, value, ValueOp.NBT);
    }

    public static CommonCondition like(String field, Object value) {
        return valueCondition(field, value, ValueOp.LIKE);
    }

    public static CommonCondition unLike(String field, Object value) {
        return valueCondition(field, value, ValueOp.NLIKE);
    }
}
