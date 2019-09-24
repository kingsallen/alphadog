package com.moseeker.baseorm.exception;

import com.moseeker.common.exception.CommonException;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/7/19 3:59 PM
 * @Version 1.0
 */
public class CommonServiceException extends CommonException {

    public static final CommonException CONFIG_SERVER_ERROR = new CommonException(90019, "无法从配置服务器获取配置信息");
}
