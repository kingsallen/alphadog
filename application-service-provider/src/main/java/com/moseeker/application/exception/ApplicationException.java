package com.moseeker.application.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by jack on 22/12/2017.
 */
public class ApplicationException extends CommonException {

    public static final CommonException APPLICATION_POSITION_NOTEXIST = new CommonException(41008,"职位信息不正确！");
    public static final CommonException APPLICATION_VALIDATE_COUNT_CHECK = new CommonException(41001, "本月您已达到投递次数上限!");
    public static final CommonException APPLICATION_POSITION_DUPLICATE = new CommonException(41002,"该职位已经申请过!");
    public static final CommonException APPLICATION_ARCHIVE_FAILED = new CommonException(41003,  "申请归档失败!" );
    public static final CommonException APPLICATION_POSITION_NOT_EXIST = new CommonException(41004,  "申请的职位不存在!" );
    public static final CommonException APPLICATION_POSITION_STATUS_STOP = new CommonException(41005,  "申请的职位已下线!" );
    public static final CommonException APPLICATION_USER_INVALID = new CommonException(41006,  "申请人是无法校验的用户!" );
    public static final CommonException APPLICATION_SOURCE_NOTEXIST = new CommonException(41007,  "申请来源不存在!" );
    public static final CommonException APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK = new CommonException(41008,  "本月您申请校招职位已达到投递次数上限!" );
    public static final CommonException APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK = new CommonException(41009,  "本月您申请社招已达到投递次数上限!" );
    public static final CommonException APPLICATION_HAVE_NO_PERMISSION = new CommonException(41010,  "没有权限查看申请信息!" );
    public static final CommonException APPLICATION_ENTITY_BUILD_FAILED = new CommonException(41011,  "实体构建失败!" );
    public static final CommonException APPLICATION_HR_ILLEGAL = new CommonException(41012,  "请提交正确的HR信息!" );
    public static final CommonException APPLICATION_HR_ACCOUT_TYPE_ILLEGAL = new CommonException(41013,  "HR账号类型异常!" );
    public static final CommonException APPLICATION_APPLICATION_ELLEGAL = new CommonException(41013,  "申请信息不正确!" );
}
