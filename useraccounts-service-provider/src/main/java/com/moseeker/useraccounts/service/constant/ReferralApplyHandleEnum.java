package com.moseeker.useraccounts.service.constant;

import com.moseeker.entity.Constant.ApplicationSource;

/**
 * 由推荐产生的申请，需要将该候选人的处理状态修改掉 0 未处理 1 邀请投递 2 不熟悉 3 推荐ta 4 邀请投递后候选人自投
 */
public enum  ReferralApplyHandleEnum {
    /**
     * 由推荐产生的申请，需要将该候选人的处理状态修改掉 0 未处理 1 邀请投递 2 不熟悉 3 推荐ta 4 转发投递 5 自投
     */
    unHandle(0),
    invite(1),
    unFamiliar(2),
    recommend(3),
    shareApply(4),
    selfApply(5),;

    private int type;

    ReferralApplyHandleEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static int getByApplicationSource(int origin){
        if((origin | ApplicationSource.INVITE_REFERRAL.getValue()) == origin){
            return invite.getType();
        }else if((origin | ApplicationSource.SEEK_REFERRAL.getValue()) == origin){
            return recommend.getType();
        }else if((origin | ApplicationSource.FORWARD_APPLICATION.getValue()) == origin){
            return shareApply.getType();
        }else if((origin | ApplicationSource.EMPLOYEE_CHATBOT.getValue()) == origin || (origin | ApplicationSource.EMPLOYEE_REFERRAL.getValue()) == origin){
            return 0;
        }else {
            return selfApply.getType();
        }
    }
}
