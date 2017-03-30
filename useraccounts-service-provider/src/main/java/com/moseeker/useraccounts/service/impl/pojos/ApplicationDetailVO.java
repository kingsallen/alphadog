package com.moseeker.useraccounts.service.impl.pojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 20/02/2017.
 */
public class ApplicationDetailVO {

    private int pid;
    private String position_title;
    private String company_name;
    private int step;
    private int step_status;
    private List<ApplicationOperationRecordVO> recordVOList;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPosition_title() {
        return position_title;
    }

    public void setPosition_title(String position_title) {
        this.position_title = position_title;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStep_status() {
        return step_status;
    }

    public void setStep_status(int step_status) {
        this.step_status = step_status;
    }

    public List<ApplicationOperationRecordVO> getRecordVOList() {
        return recordVOList;
    }

    public void setRecordVOList(List<ApplicationOperationRecordVO> recordVOList) {
        this.recordVOList = recordVOList;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void addApplicationOprationRecordVO(ApplicationOperationRecordVO applicationOprationRecordVO) {
        if(recordVOList == null) {
            this.recordVOList = new ArrayList<>();
        }
        this.recordVOList.add(applicationOprationRecordVO);
    }
}
