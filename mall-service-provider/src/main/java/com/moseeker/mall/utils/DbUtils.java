package com.moseeker.mall.utils;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 数据库操作工具
 *
 * @author cjm
 * @date 2018-10-19 14:51
 **/
public class DbUtils {

    private static final int TRY_TIMES = 3;

    public static void checkRetryTimes(int retryTimes) throws BIZException {
        if(retryTimes >= TRY_TIMES){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }
    }

}
