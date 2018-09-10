package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/7
 */
public class ApplicationException extends CommonException {

    public static final ApplicationException APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED= new ApplicationException(41020,  "未能够正常保存!" );
    public static final ApplicationException APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK = new ApplicationException(41009, "本月您申请社招职位已达到投递次数上限!");
    public static final ApplicationException APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK = new ApplicationException(41008, "本月您申请校招职位已达到投递次数上限!");

    public static final ApplicationException APPLICATION_POSITION_NOTEXIST = new ApplicationException(41021,"职位信息不正确！");
    public static final ApplicationException APPLICATION_REFERRAL_TYPE_NOT_EXIST = new ApplicationException(41523,"员工推荐候选人类型不正确！");

    public ApplicationException(int code, String message) {
        super(code, message);
    }
}
