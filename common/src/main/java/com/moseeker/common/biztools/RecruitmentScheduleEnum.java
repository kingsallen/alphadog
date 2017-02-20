package com.moseeker.common.biztools;

import com.moseeker.common.exception.RecruitmentScheduleLastStepNotExistException;

import java.util.HashMap;
import java.util.Map;

/**
 * 招聘进度
 * Created by jack on 17/02/2017.
 */
public enum RecruitmentScheduleEnum {
    APPLY(1),
    INTERVIEW(2),
    HIRED(3),
    REJECT(4),
    INTERVIEW_PENDING(5),
    CV_CHECKED(6),
    RECOM_CLICK(7),
    CV_FORWARDED(8),
    CV_PENDING(9),
    CV_PASSED(10),
    OFFER_ACCEPT(11),
    OFFERED(12),
    IMPROVE_CANDIDATE(13);

    RecruitmentScheduleEnum(int value) {
        init(value);
    }

    private static Map<Integer, RecruitmentScheduleEnum> recruitmentScheduleEnumHashMap = new HashMap<>();

    static {                                                    // Initialize map from constant name to enum constant
        for (RecruitmentScheduleEnum op : values())
            recruitmentScheduleEnumHashMap.put(op.id, op);
    }

    public static RecruitmentScheduleEnum createFromID(int id) {
        return recruitmentScheduleEnumHashMap.get(id);
    }

    /**
     * 个人中心推荐历史记录的信息
     * @return
     * @throws RecruitmentScheduleLastStepNotExistException 招聘进度状态不存在
     */
    public int getDisplayStatus() throws RecruitmentScheduleLastStepNotExistException {
        int value;
        switch (this.id) {
            case 1:
            case 7 :
            case 13 : value =  1;break;
            case 6: value = 2; break;
            case 8:
            case 9:
            case 10: value = 3; break;
            case 2:
            case 5:
            case 12 : value = 4; break;
            case 3:
            case 11: value = 5; break;
            case 4:
                if(lastID == 0 || lastID > 13) {
                    throw new RecruitmentScheduleLastStepNotExistException();
                } else {
                    switch (lastID) {
                        case 1:
                        case 7 :
                        case 13 : value =  6;break;
                        case 6:  value = 7; break;
                        case 8:
                        case 9:
                        case 10: value = 8; break;
                        case 2:
                        case 5:
                        case 12 : value = 9; break;
                        default: value = 0;
                    }
                }
                break;
            default : value = 0;

        }
        return value;
    }

    /**
     * 设置上一个进度
     * @param lastStep
     * @throws RecruitmentScheduleLastStepNotExistException
     */
    public void setLastStep(int lastStep) throws RecruitmentScheduleLastStepNotExistException {
        if(lastStep == 0 || lastStep > 13) {
            throw new RecruitmentScheduleLastStepNotExistException();
        }
        this.lastID = lastStep;
    }

    private void init(int value) {
        switch (value) {
            case 1:
                this.id = value;
                this.status = "被推荐人投递简历";
                this.award = 10;
                this.description = "Apply 简历提交成功";
                this.disable = true;
                this.priority = 3;
                this.recuritOrder = 3;
                break;
            case 2:
                this.id = value;
                this.status = "入职";
                this.award = 500;
                this.description = "Hired 入职";
                this.disable = true;
                this.priority = 12;
                this.recuritOrder = 12;
                break;
            case 3:
                this.id = value;
                this.status = "HR已经安排面试";
                this.award = 50;
                this.description = "Interview HR已经安排面试";
                this.disable = true;
                this.priority = 8;
                this.recuritOrder = 8;
                break;
            case 4:
                this.id = value;
                this.status = "拒绝";
                this.award = 0;
                this.description = "Reject 拒绝";
                this.disable = true;
                this.priority = 13;
                this.recuritOrder = 13;
                break;
            case 5:
                this.id = value;
                this.status = "MGR面试后表示先等待";
                this.award = 0;
                this.description = "IntvPending MGR面试后表示先等待";
                this.disable = true;
                this.priority = 9;
                this.recuritOrder = 9;
                break;
            case 6:
                this.id = value;
                this.status = "简历被HR查看/简历被下载";
                this.award = 0;
                this.description = "CVChecked 简历被HR查看/简历被下载";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 4;
                break;
            case 7:
                this.id = value;
                this.status = "转发职位被点击";
                this.award = 1;
                this.description = "RecomClick 转发被点击";
                this.disable = true;
                this.priority = 1;
                this.recuritOrder = 1;
                break;
            case 8:
                this.id = value;
                this.status = "HR将简历转给MGR评审";
                this.award = 0;
                this.description = "CVForwarded HR将简历转给MGR评审";
                this.disable = true;
                this.priority = 5;
                this.recuritOrder = 5;
                break;
            case 9:
                this.id = value;
                this.status = "MGR评审后表示先等待";
                this.award = 0;
                this.description = "CVPending MGR评审后表示先等待";
                this.disable = true;
                this.priority = 6;
                this.recuritOrder = 6;
                break;
            case 10:
                this.id = value;
                this.status = "简历评审合格";
                this.award = 50;
                this.description = "CVPassed MGR评审通过要求面试";
                this.disable = true;
                this.priority = 7;
                this.recuritOrder = 7;
                break;
            case 11:
                this.id = value;
                this.status = "接受录取通知";
                this.award = 200;
                this.description = "OfferAccepted 接受录取通知";
                this.disable = true;
                this.priority = 11;
                this.recuritOrder = 11;
                break;
            case 12:
                this.id = value;
                this.status = "接受录取通知";
                this.award = 200;
                this.description = "OfferAccepted 接受录取通知";
                this.disable = true;
                this.priority = 11;
                this.recuritOrder = 11;
                break;
            case 13:
                this.id = value;
                this.status = "完善被推荐人信息";
                this.award = 20;
                this.description = "完善被推荐人信息";
                this.disable = true;
                this.priority = 2;
                this.recuritOrder = 2;
                break;
        }
    }

    private int id;
    private String status;
    private int award;
    private String description;
    private boolean disable;
    private int priority;
    private int recuritOrder;
    private int lastID;

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getAward() {
        return award;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDisable() {
        return disable;
    }

    public int getPriority() {
        return priority;
    }

    public int getRecuritOrder() {
        return recuritOrder;
    }

    public int getLastID() {
        return lastID;
    }
}
