package com.moseeker.consistencysuport.db;

/**
 *
 * 用于记录注册进来业务处理方
 *
 * Created by jack on 03/04/2018.
 */
public class Business {

    private int id;                 //业务编号
    private long registerTime;      //业务注册时间
    private long updateTime;        //记录修改时间
    private String name;            //业务名称
    private boolean finish;         //业务是否已经成功处理
    private long lastShakeHandTime; //最后一次握手时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public long getLastShakeHandTime() {
        return lastShakeHandTime;
    }

    public void setLastShakeHandTime(long lastShakeHandTime) {
        this.lastShakeHandTime = lastShakeHandTime;
    }
}
