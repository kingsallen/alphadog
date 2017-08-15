package com.moseeker.profile.exception;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 错误类工厂
 * Created by jack on 09/07/2017.
 */
public class ExceptionFactory extends com.moseeker.common.exception.ExceptionFactory {

    /**
     * 创建异常业务信息
     * @param category 错误类型
     * @return
     * @throws ParamIllegalException
     */
    public static CommonException buildException(Category category) throws ParamIllegalException {
        if(category == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(category.getCode(), category.getMsg());
    }
}
