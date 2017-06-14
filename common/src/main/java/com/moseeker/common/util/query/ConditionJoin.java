package com.moseeker.common.util.query;

/**
 * Created by jack on 24/04/2017.
 */
public class ConditionJoin {

    private ConditionOp op;             //比较操作
    private Condition condition;        //同一级别比较条件

    public ConditionJoin(ConditionOp op, Condition condition) {
        this.op = op;
        this.condition = condition;
    }

    public static ConditionJoin buildCondition(ConditionOp op, Condition condition) {
        return new ConditionJoin(op, condition);
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
}
