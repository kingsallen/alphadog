package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class InnerCondition {

    private Condition firstCondition;
    private Condition secondCondition;
    private ConditionOp conditionOp;

    public InnerCondition(Condition firstCondition, Condition secondCondition, ConditionOp conditionOp) {
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
        this.conditionOp = conditionOp;
    }

    public Condition getFirstCondition() {
        return firstCondition;
    }

    public void setFirstCondition(Condition firstCondition) {
        this.firstCondition = firstCondition;
    }

    public Condition getSecondCondition() {
        return secondCondition;
    }

    public void setSecondCondition(Condition secondCondition) {
        this.secondCondition = secondCondition;
    }
}
