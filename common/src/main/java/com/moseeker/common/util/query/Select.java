package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Select {

    private String field;
    private SelectOp selectOp;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SelectOp getSelectOp() {
        return selectOp;
    }

    public void setSelectOp(SelectOp selectOp) {
        this.selectOp = selectOp;
    }
}
