package com.moseeker.application.service.event;

import java.util.List;

/**
 *
 * HR查看申请记录的消息体
 *
 * Created by jack on 24/01/2018.
 */
public class ViewApplicationSource {

    public ViewApplicationSource(int hrId, List<Integer> applicationIdList) {
        this.applicationIdList = applicationIdList;
        this.hrId = hrId;
    }

    private List<Integer> applicationIdList;   //申请编号列表
    private int hrId;                           //HR编号

    public List<Integer> getApplicationIdList() {
        return applicationIdList;
    }

    public int getHrId() {
        return hrId;
    }
}
