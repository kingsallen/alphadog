package com.moseeker.common.util.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by jack on 24/04/2017.
 */
public class ConditionNotExist extends CommonException {

    @Override
    public String getMessage() {
        return "查询条件不存在！";
    }
}
