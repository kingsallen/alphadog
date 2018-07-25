package com.moseeker.common.providerutils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moseeker on 2017/5/19.
 */
public class ExceptionUtils {

    private static Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    public static CommonException getCommonException(String constantErrorMsg) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorMsg);
        return new CommonException(jsonObject.getIntValue("status"), jsonObject.getString("message"));
    }

    public static BIZException getBizException(String constantErrorMsg) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorMsg);
        return new BIZException(jsonObject.getIntValue("status"), jsonObject.getString("message"));
    }

    public static TException convertException(Throwable e) {
        if (e instanceof BIZException) {
            return (BIZException) e;
        } else if (e instanceof CommonException) {
            return new BIZException(((CommonException) e).getCode(), e.getMessage());
        } else if (e instanceof TException) {
            return (TException) e;
        } else {
            logger.error(e.getMessage(), e);
            return new BIZException(1, "发生异常，请稍候再试!");
        }
    }

    public static CommonException convertToCommonException(Throwable e) {
        if (e instanceof CommonException) {
            return (CommonException) e;
        } else if (e instanceof BIZException ) {
            return new CommonException(((BIZException)e).getCode(), e.getMessage());
        } else if (e instanceof TException) {
            return CommonException.PROGRAM_EXCEPTION;
        } else {
            return CommonException.NODATA_EXCEPTION;
        }
    }
}
