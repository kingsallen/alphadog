package com.moseeker.candidate.service.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by zhangdi on 2017/8/24.
 */
public class CandidateException extends CommonException {


    public CandidateException(int code, String message) {
        super(code, message);
    }
}
