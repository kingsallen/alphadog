package com.moseeker.consistencysuport;

import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.manager.ProducerConsistentManager;
import com.moseeker.consistencysuport.manager.ProducerManagerSpringProxy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *
 * 调用方数据一致性工具入口的代理类
 *
 * Created by jack on 02/04/2018.
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProducerEntryProxy {

    @Autowired
    DefaultDSLContext context;

    @Autowired
    ProducerManagerSpringProxy config;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 切入点
     */
    private static final String POINCUT = "@within(com.moseeker.consistencysuport.ConsumerEntry) || @annotation(com.moseeker.consistencysuport.ConsumerEntry)";

    /**
     *
     * @param call
     * @param consumerEntry
     * @throws ConsistencyException
     */
    @AfterReturning(value = POINCUT)
    public void afterReturn(JoinPoint call, ConsumerEntry consumerEntry) throws ConsistencyException {

        
    }
}