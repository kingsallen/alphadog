package com.moseeker.application.domain.pojo;

/**
 * 申请状态
 * Created by jack on 18/01/2018.
 */
public class ApplicationStatePojo {

    private int id;             //申请编号
    private int state;          //当前状态
    private int preState;       //更改前状态
    private int view;           //0 查阅，1 未查阅

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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
