package com.moseeker.consistencysuport.exception;

import com.moseeker.common.exception.CommonException;

/**
 *
 * 一致性工具异常
 *
 * Created by jack on 03/04/2018.
 */
public class ConsistencyException extends CommonException {

    public ConsistencyException(int status, String message) {
        super(status, message);
    }

    public static final ConsistencyException CONSISTENCY_CONFLICTS_CONVERTTOOL = new ConsistencyException(81001, "重复的参数转换工具");
    public static final ConsistencyException CONSISTENCY_UNBIND_CONVERTTOOL = new ConsistencyException(81002, "重复的参数转换工具");
}
