package com.moseeker.candidate.service.exception;

import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.common.exception.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 候选人服务业务异常工厂
 * Created by jack on 07/04/2017.
 */
public class CandidateExceptionFactory extends ExceptionFactory {

    private Logger logger = LoggerFactory.getLogger(CandidateExceptionFactory.class);

    public CandidateExceptionFactory() {
        logger.info("init candidate exception");
        for(Category category : Category.values()) {
            CommonException bizException = new CommonException(category.getCode(), category.getMsg());
            addException(bizException);
        }
        for (CandidateCategory candidateCategory : CandidateCategory.values()) {
            CommonException bizException = new CommonException(candidateCategory.getCode(), candidateCategory.getMsg());
            addException(bizException);
            logger.info("exception type -- code:{0},  msg:{1}", candidateCategory.getCode(), candidateCategory.getMsg());
        }
    }

    public static CommonException buildException(CandidateCategory candidateCategory) throws ParamIllegalException {
        if(candidateCategory == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(candidateCategory.getCode(), candidateCategory.getMsg());
    }

    public static CommonException buildException(Category category) throws ParamIllegalException {
        if(category != null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(category.getCode(), category.getMsg());
    }

    /**
     * 创建未开启挖掘被动求职者异常
     * @return 未开启挖掘被动求职者异常
     */
    public static CommonException buildNotStartPassiveSeekerException() {
        return buildException(61001);
    }

    /**
     * 创建自定义业务异常。这些异常不入异常对象池
     * @param code 异常状态码
     * @param msg 异常消息
     * @return 异常信息
     */
    public static CommonException buildCustomException(int code, String msg) {
        CommonException bizException = new CommonException(code, msg);
        return bizException;
    }

    /**
     * 创建
     * @param msg
     * @return
     */
    public static CommonException buildCheckFailedException(String msg) {
        return buildCustomException(90014, msg);
    }
}
