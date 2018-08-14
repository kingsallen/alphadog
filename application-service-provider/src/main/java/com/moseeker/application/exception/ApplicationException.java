package com.moseeker.application.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by jack on 22/12/2017.
 */
public class ApplicationException extends CommonException {

    public static final ApplicationException APPLICATION_POSITION_NOTEXIST = new ApplicationException(41008,"职位信息不正确！");
    public static final ApplicationException APPLICATION_VALIDATE_COUNT_CHECK = new ApplicationException(41001, "本月您已达到投递次数上限!");
    public static final ApplicationException APPLICATION_POSITION_DUPLICATE = new ApplicationException(41002,"该职位已经申请过!");
    public static final ApplicationException APPLICATION_ARCHIVE_FAILED = new ApplicationException(41003,  "申请归档失败!" );
    public static final ApplicationException APPLICATION_POSITION_NOT_EXIST = new ApplicationException(41004,  "申请的职位不存在!" );
    public static final ApplicationException APPLICATION_POSITION_STATUS_STOP = new ApplicationException(41005,  "申请的职位已下线!" );
    public static final ApplicationException APPLICATION_USER_INVALID = new ApplicationException(41006,  "申请人是无法校验的用户!" );
    public static final ApplicationException APPLICATION_SOURCE_NOTEXIST = new ApplicationException(41007,  "申请来源不存在!" );
    public static final ApplicationException APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK = new ApplicationException(41008,  "本月您申请校招职位已达到投递次数上限!" );
    public static final ApplicationException APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK = new ApplicationException(41009,  "本月您申请社招已达到投递次数上限!" );
    public static final ApplicationException APPLICATION_HAVE_NO_PERMISSION = new ApplicationException(41010,  "没有权限查看申请信息!" );
    public static final ApplicationException APPLICATION_ENTITY_BUILD_FAILED = new ApplicationException(41011,  "实体构建失败!" );
    public static final ApplicationException APPLICATION_HR_ILLEGAL = new ApplicationException(41012,  "请提交正确的HR信息!" );
    public static final ApplicationException APPLICATION_HR_ACCOUT_TYPE_ILLEGAL = new ApplicationException(41013,  "HR账号类型异常!" );
    public static final ApplicationException APPLICATION_APPLICATION_ELLEGAL = new ApplicationException(41014,  "申请信息不正确!" );
    public static final ApplicationException APPLICATION_REDIS_CLIENT = new ApplicationException(41015,  "缺少Redis依赖!" );
    public static final ApplicationException APPLICATION_NOTICCE_CLOSE = new ApplicationException(41016,  "模板开关关闭!" );
    public static final ApplicationException APPLICATION_EMPLOYEE_NOT_EXIST = new ApplicationException(41017,  "员工信息不正确!" );
    public static final ApplicationException APPLICATION_POSITIONS_NOT_LEGAL = new ApplicationException(41018,  "职位信息错误!" );
    public static final ApplicationException APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED= new ApplicationException(41019,  "自定义职位校验失败!" );
    public static final ApplicationException APPLICATION_CREATE_FAILED= new ApplicationException(41020,  "申请创建失败!" );

    public ApplicationException(int code, String message) {
        super(code, message);
    }
}