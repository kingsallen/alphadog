package com.moseeker.commonservice.annotation.aop;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author ltf 接口统计 Aop 2016年10月31日
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ExceptionTransferAop implements Ordered {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 切入点
     */
    private static final String POINCUT = "@within(com.moseeker.commonservice.annotation.iface.ExceptionTransfer) || @annotation(com.moseeker.commonservice.annotation.iface.ExceptionTransfer)";

    /**
     * throws exception
     */
    @AfterThrowing(value = POINCUT, throwing = "e")
    public void afterThrowing(Exception e) throws BIZException {
        if (e instanceof CommonException) {
            logger.info("ExceptionTransferAop CommonException");
            throw ExceptionConvertUtil.convertCommonException((CommonException)e);
        } else {
            logger.info("ExceptionTransferAop is not CommonException");
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
