package com.moseeker.application.domain.pojo;

/**
 * 申请状态
 * Created by jack on 18/01/2018.
 */
public class ApplicationStatePojo {

    private int id;                 //申请编号
    private int state;              //当前状态
    private int preState;           //更改前状态
    private boolean addViewOnly;    //只增加浏览次数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPreState() {
        return preState;
    }

    public void setPreState(int preState) {
        this.preState = preState;
    }

    public boolean isAddViewOnly() {
        return addViewOnly;
    }

    public void setAddViewOnly(boolean addViewOnly) {
        this.addViewOnly = addViewOnly;
    }
}
