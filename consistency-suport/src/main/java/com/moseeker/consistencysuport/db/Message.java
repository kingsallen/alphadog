package com.moseeker.consistencysuport.db;

import com.moseeker.common.validation.ValidateUtil;

import java.util.List;

/**
 *
 * 数据库消息记录
 *
 * Created by jack on 03/04/2018.
 */
public class Message {
    private String messageId;   //消息ID
    private String name;        //业务名称
    private long createTime;    //创建时间
    private long updateTime;    //修改时间
    private int version;        //版本
    private List<Business> businessList;    //业务
    private int retry;          //重试次数
    private long lastRetryTime; //上一次重试的时间
    private String param;       //参数
    private boolean finish;     //是否完成
    private String className;   //类名
    private String method;      //方法名
    private int period;         //时间间隔 单位是秒

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public long getLastRetryTime() {
        return lastRetryTime;
    }

    public void setLastRetryTime(long lastRetryTime) {
        this.lastRetryTime = lastRetryTime;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
