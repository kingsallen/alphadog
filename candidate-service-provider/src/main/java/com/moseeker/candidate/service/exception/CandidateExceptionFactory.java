package com.moseeker.candidate.service.exception;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.ExceptionFactory;

import java.util.HashMap;

/**
 * 候选人服务业务异常工厂
 * Created by jack on 07/04/2017.
 */
public class CandidateExceptionFactory extends ExceptionFactory {

    private CandidateExceptionType candidateExceptionType = new CandidateExceptionType();

    public CandidateExceptionFactory() {
        HashMap<Integer, String> exceptionTypePool = candidateExceptionType.getExceptionTypePool();
        if(exceptionTypePool != null && exceptionTypePool.size() > 0) {
            exceptionTypePool.forEach((code, msg) -> {
                BIZException bizException = new BIZException(code, msg);
                addException(bizException);
            });
        }
    }

    /**
     * 创建自定义业务异常。这些异常不入异常对象池
     * @param code 异常状态码
     * @param msg 异常消息
     * @return 异常信息
     */
    public static BIZException buildCustomException(int code, String msg) {
        BIZException bizException = new BIZException(code, msg);
        return bizException;
    }

    /**
     * 创建
     * @param msg
     * @return
     */
    public static BIZException buildCheckFailedException(String msg) {
        return buildCustomException(90014, msg);
    }
}
