package com.moseeker.application.context.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 浏览申请事件
 * Created by jack on 17/01/2018.
 */
public class ViewApplicationListEvent extends ApplicationEvent {

    private List<Integer> applicationIdList;    //申请编号
    private int hrId;                           //HR编号

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ViewApplicationListEvent(Object source) {
        super(source);
    }

    public List<Integer> getApplicationIdList() {
        return applicationIdList;
    }

    public void setApplicationIdList(List<Integer> applicationIdList) {
        this.applicationIdList = applicationIdList;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }
}