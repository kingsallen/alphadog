package com.moseeker.position.utils;

/**
 * Created by YYF
 *
 * Date: 2017/4/18
 *
 * Project_name :alphadog
 */
public enum CommonMessage {

    EXCEPTION("发生异常，请稍候再试!", 9999),
    SUCCESS("SUCCESS", 0),
    HEAD_IMAGE_BLANK("无头图信息", 51013),
    POSITIONID_BLANK("PositionId不能为空", 51014),
    POSITION_NONEXIST("该职位不存在，请检查PositionId是否正确", 51015),
    COMPANYID_BLANK("CompanyId不能为空", 51016),
    POSITIONLIST_NONEXIST("该公司下没有职位", 51017),
    TEAM_NONEXIST("该团队不存在，请检查TeamId是否正确", 51018);


    // 成员变量
    private String message;
    private Integer status;

    // 构造方法
    CommonMessage(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
