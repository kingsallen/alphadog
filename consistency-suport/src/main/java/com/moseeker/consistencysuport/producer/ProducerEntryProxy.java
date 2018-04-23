package com.moseeker.consistencysuport.producer;

import com.moseeker.consistencysuport.common.ParamConvertTool;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
    ProducerManagerSpringProxy config;

    @Autowired
    ApplicationContext applicationContext;

    /**
     *
     * @param call
     * @param producerEntry
     * @throws ConsistencyException
     */
    @Before(value = "@within(producerEntry) || @annotation(producerEntry)")
    public void before(JoinPoint call, ProducerEntry producerEntry) throws ConsistencyException {

        ProducerConsistentManager manager = config.buildManager();

        Optional<ParamConvertTool> paramConvertToolOptional = manager.getParamConvertTool(producerEntry.name());
        if (!paramConvertToolOptional.isPresent()) {
            manager.notification(ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL);
            throw ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL;
        }
        Object[] objects = call.getArgs();
        if (producerEntry.index() >= objects.length) {
            manager.notification(ConsistencyException.CONSISTENCY_PRODUCER_LOST_MESSAGEID);
            throw ConsistencyException.CONSISTENCY_PRODUCER_LOST_MESSAGEID;
        }
        String className = call.getTarget().getClass().getName();
        String method = call.getSignature().getName();
        String name = producerEntry.name();
        int period = producerEntry.period();

        String messageId = objects[producerEntry.index()].toString();

        manager.logMessage(messageId, name, className, method, objects, period);
    }
}