package com.moseeker.common.util.query;

/**
 * Created by jack on 24/04/2017.
 */
public class ConditionJoin {

    private ConditionOp op;             //比较操作
    private Condition condition;        //同一级别比较条件
    private Condition innerCondition;   //上一级别的优先查询条件

    public ConditionJoin(ConditionOp op, Condition condition, Condition innerCondition) {
        this.op = op;
        this.condition = condition;
        this.innerCondition = innerCondition;
    }

    public static ConditionJoin buildInnerCondition(ConditionOp op, Condition condition) {
        return new ConditionJoin(op, null, condition);
    }

    public static ConditionJoin buildCondition(ConditionOp op, Condition condition) {
        return new ConditionJoin(op, condition, null);
    }

    public ConditionOp getOp() {
        return op;
    }

    public void setOp(ConditionOp op) {
        this.op = op;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getInnerCondition() {
        return innerCondition;
    }

    public void setInnerCondition(Condition innerCondition) {
        this.innerCondition = innerCondition;
    }
}
