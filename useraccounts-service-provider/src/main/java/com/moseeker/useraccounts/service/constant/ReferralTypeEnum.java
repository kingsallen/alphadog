package com.moseeker.useraccounts.service.constant;

import com.moseeker.entity.Constant.ApplicationSource;

/**
 *
 * @author cjm
 * @date 2018/12/28
 */
public enum ReferralTypeEnum {
    /**
     * 推荐类型 1 直接推荐 2 职位分享自投 3 求推荐
     */
    DIRECT_REFERRAL(1),
    SHARE_REFERRAL(2),
    SEEK_REFERRAL(3),
    ;

    int type;

    ReferralTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static ReferralTypeEnum getReferralTypeByApplySource(int source){
        // 如果是员工上传简历附件，认为是直接推荐
        if(source == ApplicationSource.EMPLOYEE_CHATBOT.getValue() || source == ApplicationSource.EMPLOYEE_REFERRAL.getValue()){
            return DIRECT_REFERRAL;
        }else if(source == ApplicationSource.SEEK_REFERRAL.getValue() ||
                source == ApplicationSource.INVITE_REFERRAL.getValue()){
            // 如果是通过联系内推，目前有三个入口 1.邀请投递进入职位详情的联系内推 2.连连看进入职位详情的联系内通 3.转发职位进入职位详情的联系内推
            return SEEK_REFERRAL;
        }else {
            return SHARE_REFERRAL;
        }
    }

}