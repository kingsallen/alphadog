package com.moseeker.common.providerutils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.thrift.TException;

/**
 * Created by moseeker on 2017/5/19.
 */
public class ExceptionUtils {

    public static CommonException getCommonException(String constantErrorMsg) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorMsg);
        return new CommonException(jsonObject.getIntValue("status"), jsonObject.getString("message"));
    }

    public static BIZException getBizException(String constantErrorMsg) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorMsg);
        return new BIZException(jsonObject.getIntValue("status"), jsonObject.getString("message"));
    }

    public static TException convertException(Throwable e) throws TException {
        if (e instanceof BIZException) {
            return (BIZException) e;
        } else if (e instanceof CommonException) {
            return new BIZException(((CommonException) e).getCode(), e.getMessage());
        } else if (e instanceof TException) {
            return (TException) e;
        } else {
            return new BIZException(1, "发生异常，请稍候再试!");
        }
    }
}
