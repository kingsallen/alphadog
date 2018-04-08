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
import java.util.UUID;

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
    private static final String POINCUT = "@within(com.moseeker.consistencysuport.ProducerEntry) || @annotation(com.moseeker.consistencysuport.ProducerEntry)";

    /**
     *
     * @param call
     */
    @AfterReturning(value = POINCUT)
    public void afterReturn(JoinPoint call, ProducerEntry producerEntry) throws ConsistencyException {

        config.buildMessageHandler(context);
        ProducerConsistentManager manager = config.buildManager();
        Optional<ParamConvertTool> paramConvertToolOptional = manager.getParamConvertTool(producerEntry.name());
        if (!paramConvertToolOptional.isPresent()) {
            throw ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL;
        }
        String className = producerEntry.className();
        String method = producerEntry.method();
        String params = producerEntry.params();
        int period = producerEntry.period();
        manager.logMessage(UUID.randomUUID().toString(), producerEntry.name(), params, className, method, period);
    }
}