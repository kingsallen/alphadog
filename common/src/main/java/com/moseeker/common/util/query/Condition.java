package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Condition {

    private InnerCondition firstCondition;
    private ValueCondition secondCondition;

    public InnerCondition getFirstCondition() {
        return firstCondition;
    }

    public void setFirstCondition(InnerCondition firstCondition) {
        this.firstCondition = firstCondition;
    }

    public ValueCondition getSecondCondition() {
        return secondCondition;
    }

    public void setSecondCondition(ValueCondition secondCondition) {
        this.secondCondition = secondCondition;
    }
}
