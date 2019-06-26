package com.moseeker.common.biztools;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.RecruitmentScheduleLastStepNotExistException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 招聘进度
 * Created by jack on 17/02/2017.
 */
public enum RecruitmentScheduleEnum {
    APPLY(Constant.RECRUIT_STATUS_APPLY),
    EMPLOYEE_REFERRAL(Constant.RECRUIT_STATUS_UPLOAD_PROFILE),
    INTERVIEW(Constant.RECRUIT_STATUS_INTERVIEW),
    HIRED(Constant.RECRUIT_STATUS_HIRED),
    REJECT(Constant.RECRUIT_STATUS_REJECT),
    INTERVIEW_PENDING(Constant.RECRUIT_STATUS_INTERVIEWPENDING),
    CV_CHECKED(Constant.RECRUIT_STATUS_CVCHECKED),
    RECOM_CLICK(Constant.RECRUIT_STATUS_RECOMCLICK),
    CV_FORWARDED(Constant.RECRUIT_STATUS_CVFORWARDED),
    CV_PENDING(Constant.RECRUIT_STATUS_CVPENDING),
    CV_PASSED(Constant.RECRUIT_STATUS_CVPASSED),
    OFFER_ACCEPT(Constant.RECRUIT_STATUS_OFFERACCEPTED),
    OFFERED(Constant.RECRUIT_STATUS_OFFERED),
    IMPROVE_CANDIDATE(Constant.RECRUIT_STATUS_FULL_RECOM_INFO),
    EMPLOYEE_RECOMMEND(Constant.RECRUIT_STATUS_EMPLOYEE_RECOMMEND),
    RECRUIT_STATUS_WRITTEN_EXAMINATION(Constant.RECRUIT_STATUS_WRITTEN_EXAMINATION),
    RECRUIT_STATUS_FINAL_INTERVIEW(Constant.RECRUIT_STATUS_FINAL_INTERVIEW);

    RecruitmentScheduleEnum(int value) {
        init(value);
    }

    private static Map<Integer, RecruitmentScheduleEnum> recruitmentScheduleEnumHashMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(RecruitmentScheduleEnum.class);

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
    public int getStatusForRecommendationInPersonalCenter() throws RecruitmentScheduleLastStepNotExistException {
        int value;
        switch (this.id) {
            case 1:
            case 7 :
            case 15:
            case 16:
            case 13 : value =  1;break;
            case 6: value = 2; break;
            case 8:
            case 9:
            case 10: value = 3; break;
            case 2:
            case 5:
            case 17:
            case 18:
            case 12 : value = 4; break;
            case 3:
            case 11: value = 5; break;
            case 4:
                if(lastID == 0 || lastID > 15) {
                    throw new RecruitmentScheduleLastStepNotExistException();
                } else {
                    switch (lastID) {
                        case 1:
                        case 7 :
                        case 15:
                        case 16:
                        case 13 : value = 6; break;
                        case 6:  value = 7; break;
                        case 8:
                        case 9:
                        case 10: value = 8; break;
                        case 2:
                        case 5:
                        case 17:
                        case 18:
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
     * 为个人中心求职详细信息提供招聘进度状态
     * @return
     * @throws RecruitmentScheduleLastStepNotExistException
     */
    public int getStepForApplicationDetail() throws RecruitmentScheduleLastStepNotExistException {
        int value;
        switch (this.id) {
            case 1:
            case 15:
            case 16:
            case 6: value = 1;break;
            case 8:
            case 9:
            case 10:
            case 2:
            case 12 :
            case 17:
            case 18:
            case 5: value = 2; break;

            case 11 :
            case 3: value = 3; break;
            case 4:
                if(this.getLastID() < 0 || this.getLastID() > 15) {
                    throw new RecruitmentScheduleLastStepNotExistException();
                }
                switch (lastID) {
                    case 1:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 2:
                    case 5:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        value = 2;
                        break;
                    case 12:
                    case 11:
                    case 3:
                        value = 3;
                        break;
                    case 4:
                    default: value = 2;
                }
                break;
            default: value = 0;
        }

        return value;
    }

    /**
     * 查找招聘进度的状态
     * @return 0 表示未开始，1表示通过，2表示拒绝
     * @throws RecruitmentScheduleLastStepNotExistException
     * @param emailStatus
     */
    public int getStepStatusForApplicationDetail(byte emailStatus) throws RecruitmentScheduleLastStepNotExistException {
        int value;
        switch (this.id) {
            case 4:
                value = 2;break;
            case 8:
            case 9:
            case 10:
                value = 0;break;
            case 2:
            case 1:
            case 6:
            case 5:
            case 12 :
            case 11 :
            case 3:
            case 16:
            case 15:
            case 17:
            case 18:
                if(emailStatus != EmailStatus.NOMAIL.getValue()) {
                    value = 0;
                } else {
                    value = 1;
                }
                break;
            default: value = 0;
        }

        return value;
    }

    /**
     * 设置上一个进度
     * @param lastStep
     * @throws RecruitmentScheduleLastStepNotExistException
     */
    public void setLastStep(int lastStep) throws RecruitmentScheduleLastStepNotExistException {
        if(lastStep == 0 || lastStep > 16) {
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
                this.applierView = "简历提交成功";
                break;

            case 2:
                this.id = value;
                this.status = "入职";
                this.award = 500;
                this.description = "Hired 入职";
                this.disable = true;
                this.priority = 12;
                this.recuritOrder = 12;
                this.applierView = "您已进入面试";
                break;
            case 3:
                this.id = value;
                this.status = "HR已经安排面试";
                this.award = 50;
                this.description = "Interview HR已经安排面试";
                this.disable = true;
                this.priority = 8;
                this.recuritOrder = 8;
                this.applierView = "恭喜您入职成功";
                break;
            case 4:
                this.id = value;
                this.status = "拒绝";
                this.award = 0;
                this.description = "Reject 拒绝";
                this.disable = true;
                this.priority = 13;
                this.recuritOrder = 13;
                this.applierView = "暂不合适，纳入人才库";
                break;
            case 5:
                this.id = value;
                this.status = "MGR面试后表示先等待";
                this.award = 0;
                this.description = "IntvPending MGR面试后表示先等待";
                this.disable = true;
                this.priority = 9;
                this.recuritOrder = 9;
                this.applierView = "您已进入面试";
                break;
            case 6:
                this.id = value;
                this.status = "简历被HR查看/简历被下载";
                this.award = 0;
                this.description = "CVChecked 简历被HR查看/简历被下载";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 4;
                this.applierView = "HR查看了您的简历";
                break;
            case 7:
                this.id = value;
                this.status = "转发职位被点击";
                this.award = 1;
                this.description = "RecomClick 转发被点击";
                this.disable = true;
                this.priority = 1;
                this.recuritOrder = 1;
                this.applierView = "";
                break;
            case 8:
                this.id = value;
                this.status = "HR将简历转给MGR初筛";
                this.award = 0;
                this.description = "CVForwarded HR将简历转给MGR初筛";
                this.disable = true;
                this.priority = 5;
                this.recuritOrder = 5;
                this.applierView = "HR查看了您的简历";
                break;
            case 9:
                this.id = value;
                this.status = "MGR初筛后表示先等待";
                this.award = 0;
                this.description = "CVPending MGR初筛后表示先等待";
                this.disable = true;
                this.priority = 6;
                this.recuritOrder = 6;
                this.applierView = "HR查看了您的简历";
                break;
            case 10:
                this.id = value;
                this.status = "简历初筛合格";
                this.award = 50;
                this.description = "CVPassed MGR初筛通过要求面试";
                this.disable = true;
                this.priority = 7;
                this.recuritOrder = 7;
                this.applierView = "您已进入面试";
                break;
            case 11:
                this.id = value;
                this.status = "接受录取通知";
                this.award = 200;
                this.description = "OfferAccepted 接受录取通知";
                this.disable = true;
                this.priority = 11;
                this.recuritOrder = 11;
                this.applierView = "待入职";
                break;
            case 12:
                this.id = value;
                this.status = "接受录取通知";
                this.award = 200;
                this.description = "OfferAccepted 接受录取通知";
                this.disable = true;
                this.priority = 10;
                this.recuritOrder = 10;
                this.applierView = "面试已通过";
                break;
            case 13:
                this.id = value;
                this.status = "完善被推荐人信息";
                this.award = 20;
                this.description = "完善被推荐人信息";
                this.disable = true;
                this.priority = 2;
                this.recuritOrder = 2;
                this.applierView = "";
                break;

            case 15:
                this.id = value;
                this.status = "员工上传人才简历";
                this.award = 10;
                this.description = "员工上传人才简历积分奖励";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 3;
                this.applierView = "员工上传人才简历";
                break;
            case 16:
                this.id = value;
                this.status = "内部员工推荐";
                this.award = 10;
                this.description = "联系内推员工完善推荐评价投递职位";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 3;
                this.applierView = "恭喜您已被內部员工推荐";
                break;

            case 18:
                this.id = value;
                this.status = "您已进入终面";
                this.award = 10;
                this.description = "您已进入终面";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 3;
                this.applierView = "您已进入终面";
                break;
            case 17:
                this.id = value;
                this.status = "您已进入笔试";
                this.award = 10;
                this.description = "您已进入笔试";
                this.disable = true;
                this.priority = 4;
                this.recuritOrder = 3;
                this.applierView = "您已进入笔试";
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
    private String applierView;

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

    public String getApplierView() {
        return applierView;
    }

    /**
     * 查询个人中西，申请记录状态描述
     * @param applyType
     * @param emailStatus
     * @return
     */
    public String getAppStatusDescription(byte applyType, byte emailStatus, int preID) {

        logger.info("getAppStatusDescription -- id:{}, applyType : {},  emailStatus : {}, preID : {}", id, applyType, emailStatus, preID);

        /** 如果上一条是拒绝，这一条是其他操作记录，那么现实"HR将您纳入候选名单" */
        if(id != RecruitmentScheduleEnum.REJECT.getId()
                && preID == RecruitmentScheduleEnum.REJECT.getId()) {
            return  "HR将您纳入候选名单";
        }
        if (id == RecruitmentScheduleEnum.EMPLOYEE_REFERRAL.getId()) {
            return "恭喜您已被内部员工推荐";
        }
        if(id == RecruitmentScheduleEnum.OFFERED.getId() && preID == RecruitmentScheduleEnum.HIRED.getId()) {
            return  "HR将您的状态改为待重新入职";
        }
        if(id == RecruitmentScheduleEnum.CV_PASSED.getId() && preID == RecruitmentScheduleEnum.OFFERED.getId()) {
            return  "HR将您的状态改为待重新面试";
        }
        if(id == RecruitmentScheduleEnum.CV_CHECKED.getId() && preID == RecruitmentScheduleEnum.CV_PASSED.getId()) {
            return  "HR将您的状态改为待重新筛选";
        }
        String eventDescription = applierView;
        /** 如果投递时邮件投递，并且投递状态是成功投递 */
        if (applyType == ApplyType.EMAIL.getValue()){
            if(id == RecruitmentScheduleEnum.APPLY.getId()) {
                switch (emailStatus) {
                    case 1: eventDescription = EmailStatus.NOT_ANSWER_EMAIL.getMessage();break;
                    case 2: eventDescription = EmailStatus.ATTACHMENT_NOT_SUPPORT.getMessage();break;
                    case 3: eventDescription = EmailStatus.ATTACHMENT_MORE_THEN_MAXIMUN.getMessage();break;
                    case 8: eventDescription = EmailStatus.MAIL_NOT_FOUND.getMessage();break;
                    case 9: eventDescription = EmailStatus.MAIL_PARSING_FAILED.getMessage();break;
                }
            }
        }
        logger.info("getAppStatusDescription -- eventDescription : {}", eventDescription);
        return eventDescription;
    }

    public String getAppStatusDescription(byte applyType, byte emailStatus, int preID, String name) {
        logger.info("getAppStatusDescription -- id:{}, applyType : {},  emailStatus : {}, preID : {}", id, applyType, emailStatus, preID);

        /** 如果上一条是拒绝，这一条是其他操作记录，那么现实"HR将您纳入候选名单" */
        if(id != RecruitmentScheduleEnum.REJECT.getId()
                && preID == RecruitmentScheduleEnum.REJECT.getId()) {
            return  "HR将您纳入候选名单";
        }
        if (id == RecruitmentScheduleEnum.EMPLOYEE_REFERRAL.getId()) {
            if (StringUtils.isNotBlank(name)) {
                return name+"推荐了您的简历";
            }
            return "恭喜您已被内部员工推荐";
        }
        if (id == RecruitmentScheduleEnum.EMPLOYEE_RECOMMEND.getId()) {
            if (StringUtils.isNotBlank(name)) {
                return "【"+name+"】推荐了您的简历";
            }
            return "恭喜您已被内部员工推荐";
        }
        if(id == RecruitmentScheduleEnum.OFFERED.getId() && preID == RecruitmentScheduleEnum.HIRED.getId()) {
            return  "HR将您的状态改为待重新入职";
        }
        if(id == RecruitmentScheduleEnum.CV_PASSED.getId() && preID == RecruitmentScheduleEnum.OFFERED.getId()) {
            return  "HR将您的状态改为待重新面试";
        }
        if(id == RecruitmentScheduleEnum.CV_CHECKED.getId() && preID == RecruitmentScheduleEnum.CV_PASSED.getId()) {
            return  "HR将您的状态改为待重新筛选";
        }
        String eventDescription = applierView;
        /** 如果投递时邮件投递，并且投递状态是成功投递 */
        if (applyType == ApplyType.EMAIL.getValue()){
            if(id == RecruitmentScheduleEnum.APPLY.getId()) {
                switch (emailStatus) {
                    case 1: eventDescription = EmailStatus.NOT_ANSWER_EMAIL.getMessage();break;
                    case 2: eventDescription = EmailStatus.ATTACHMENT_NOT_SUPPORT.getMessage();break;
                    case 3: eventDescription = EmailStatus.ATTACHMENT_MORE_THEN_MAXIMUN.getMessage();break;
                    case 8: eventDescription = EmailStatus.MAIL_NOT_FOUND.getMessage();break;
                    case 9: eventDescription = EmailStatus.MAIL_PARSING_FAILED.getMessage();break;
                }
            }
        }
        logger.info("getAppStatusDescription -- eventDescription : {}", eventDescription);
        return eventDescription;
    }
}
