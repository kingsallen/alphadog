package com.moseeker.position.service.schedule.constant;

/**
 * 猎聘职位审核状态
 *
 * @author cjm
 * @date 2018-07-02 12:03
 **/
public enum LiepinPositionAuditState {
    /**
     * 待审核
     */
    WAITCHECK("WAITCHECK"),
    /**
     * 审核通过
     */
    PASS("PASS"),
    /**
     * 审核未通过
     */
    NOTPASS("NOTPASS"),
    ;

    private String value;

    LiepinPositionAuditState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
