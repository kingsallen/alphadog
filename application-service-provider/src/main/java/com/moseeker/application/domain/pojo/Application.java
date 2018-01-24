package com.moseeker.application.domain.pojo;

import java.util.List;

/**
 * 申请数据
 * Created by jack on 17/01/2018.
 */
public class Application {

    private int id;             //申请编号
    private int status;         //状态
    private boolean viewed;     //是否被查看 true 查看，false 未被查看
    private List<Integer> hrId; //有权限修改招聘进度、查看申请的HR集合

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public boolean isViewed() {
        return viewed;
    }

    public List<Integer> getHrId() {
        return hrId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public void setHrId(List<Integer> hrId) {
        this.hrId = hrId;
    }
}
