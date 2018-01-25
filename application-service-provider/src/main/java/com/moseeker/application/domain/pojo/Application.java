package com.moseeker.application.domain.pojo;

import com.moseeker.application.domain.component.state.ApplicationStatus;

import java.util.List;

/**
 * 申请数据
 * Created by jack on 17/01/2018.
 */
public class Application {

    private int id;                         //申请编号
    private ApplicationStatus status;       //状态
    private boolean viewed;                 //是否被查看 true 查看，false 未被查看
    private ApplicationStatus nextStatus;   //下一个状态
    private boolean viewOnly;               //只是查看，不做招聘进度修改
    private List<Integer> hrId;             //有权限修改招聘进度、查看申请的HR集合

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public ApplicationStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(ApplicationStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public boolean isViewOnly() {
        return viewOnly;
    }

    public void setViewOnly(boolean viewOnly) {
        this.viewOnly = viewOnly;
    }

    public List<Integer> getHrId() {
        return hrId;
    }

    public void setHrId(List<Integer> hrId) {
        this.hrId = hrId;
    }
}
