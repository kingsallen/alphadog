package com.moseeker.common.exception;

/**
 * Created by jack on 17/02/2017.
 */
public class RecruitmentScheduleLastStepNotExistException extends CommonException {

    private static final long serialVersionUID = -5518273416325854839L;

    public RecruitmentScheduleLastStepNotExistException() {
        this.setMessage("未设置当前招聘进度的上一个招聘进度！");
    }
}
