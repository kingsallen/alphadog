package com.moseeker.consistencysuport.consumer;

import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 *
 * 调用方数据一致性工具入口的代理类
 *
 * Created by jack on 02/04/2018.
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConsumerEntryProxy {

    @Autowired
    ConsumerConsistentManagerSpringProxy consumerConsistentManagerSpringProxy;

    @Autowired
    ApplicationContext applicationContext;

    /**
     *
     * 方法执行完成出发
     *
     * @param call
     * @param consumerEntry
     * @param returnValue
     * @throws ConsistencyException
     */
    @AfterReturning(value = "@within(consumerEntry) || @annotation(consumerEntry)", returning = "returnValue")
    public void afterReturn(JoinPoint call, ConsumerEntry consumerEntry, Object returnValue) throws ConsistencyException {

        ConsumerConsistentManager manager = consumerConsistentManagerSpringProxy.buildConsumerConsistentManager();

        Object[] objects = call.getArgs();
        if (consumerEntry.index() >= objects.length) {
            manager.notification(ConsistencyException.CONSISTENCY_CONSUMER_LOST_MESSAGEID);
            throw ConsistencyException.CONSISTENCY_CONSUMER_LOST_MESSAGEID;
        }
        String messageId = objects[consumerEntry.index()].toString();
        String businessName = consumerEntry.businessName();
        String messageName = consumerEntry.messageName();

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("消息编号", messageId);
        validateUtil.addRequiredStringValidate("业务名称", messageName);
        validateUtil.addRequiredStringValidate("业务名称", businessName);

        String result = validateUtil.validate();

        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            throw ConsistencyException.CONSISTENCY_CONSUMER_ANNOTATION_LOGT_CONFIG;
        }

        manager.finishTask(messageId, messageName, businessName);
    }
}
