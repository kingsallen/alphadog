package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Condition {

    protected InnerCondition innerCondition;
    protected ValueCondition valueCondition;

    public InnerCondition getInnerCondition() {
        return innerCondition;
    }

    public void setInnerCondition(InnerCondition innerCondition) {
        this.innerCondition = innerCondition;
    }

    public ValueCondition getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(ValueCondition valueCondition) {
        this.valueCondition = valueCondition;
    }
}
