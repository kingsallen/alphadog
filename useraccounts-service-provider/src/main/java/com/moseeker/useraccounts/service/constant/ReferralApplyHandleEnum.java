package com.moseeker.useraccounts.service.constant;

/**
 * 由推荐产生的申请，需要将该候选人的处理状态修改掉 0 未处理 1 邀请投递 2 不熟悉 3 推荐ta 4 邀请投递后候选人自投
 */
public enum  ReferralApplyHandleEnum {
    /**
     * 由推荐产生的申请，需要将该候选人的处理状态修改掉 0 未处理 1 邀请投递 2 不熟悉 3 推荐ta 4 邀请投递后候选人自投
     */
    unHandle(0),
    invite(1),
    unFamiliar(2),
    recommend(3),
    selfApply(4),;

    private int type;

    ReferralApplyHandleEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }}
