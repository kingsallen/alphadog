package com.moseeker.common.providerutils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * Created by moseeker on 2017/5/19.
 */
public class ExceptionUtils {

    public static BIZException getBizException(String constantErrorMsg) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorMsg);
        return new BIZException(jsonObject.getIntValue("status"), jsonObject.getString("message"));
    }
}
