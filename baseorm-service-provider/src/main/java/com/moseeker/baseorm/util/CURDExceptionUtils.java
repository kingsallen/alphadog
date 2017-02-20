package com.moseeker.baseorm.util;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.dao.struct.CURDException;

/**
 * Created by jack on 15/02/2017.
 */
public class CURDExceptionUtils {

    public static CURDException buildSystemException() {
        return buildCURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    public static CURDException buildGetNothingException() {
        return buildCURDException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    public static CURDException buildPutException() {
        return buildCURDException(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    public static CURDException buildPostException() {
        return buildCURDException(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    public static CURDException buildDelException() {
        return buildCURDException(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    private static CURDException buildCURDException(String message) {
        CURDException e = new CURDException();

        JSONObject map = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        e.code = (Integer) map.get("status");
        e.message = (String) map.get("message");
        return e;
    }
}
