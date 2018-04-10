package com.moseeker.consistencysuport.manager;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.persistence.MessagePersistence;
import com.moseeker.consistencysuport.persistence.MessagePersistenceImpl;
import com.moseeker.consistencysuport.protector.InvokeHandler;
import com.moseeker.consistencysuport.protector.ProtectorTask;
import com.moseeker.consistencysuport.protector.ProtectorTaskConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 *
 * todo 可配置信息：1. 消息消费结果通知渠道；
 * todo 初始化数据一致性组件工作内容：1. 创建消息消费结果通知渠道(默认RabbitMQ)  2.创建数据库表-如果数据库不存在的话
 * todo 心跳校验: 业务是否还存在
 * todo 定义工作流程:1.初始化组件；2. 创建消息队列； 3. 启动定时任务；4.绑定消息生产方的消息出发任务; 5:处理消息（包括任务完成或者失败以及心跳消息）
 *
 * Created by jack on 02/04/2018.
 */
public class ProducerConsistentManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessagePersistence messagePersistence;                      //消息持久化工具
    private Map<String, ParamConvertTool> paramConvertToolMap;  //参数与持久化字段的转换工具
    private Notification notification;                          //通知功能
    private ProtectorTask protectorTask;                        //启动保护任务
    private InvokeHandler invokeHandler;
    private long initialDelay;
    private long period;
    private byte retriedUpper;

    private ThreadPool threadPool = ThreadPool.Instance;

    public ProducerConsistentManager(MessageRepository messageRepository,
                                     Map<String, ParamConvertTool> paramConvertToolMap, Notification notification,
                                     InvokeHandler invokeHandler,
                                     long initialDelay, long period, byte retriedUpper) throws ConsistencyException {
        this.initialDelay = initialDelay;
        this.period = period;
        this.retriedUpper = retriedUpper;
        this.messagePersistence = new MessagePersistenceImpl(messageRepository);
        this.paramConvertToolMap = paramConvertToolMap;
        this.invokeHandler = invokeHandler;
        this.protectorTask = new ProtectorTaskConfigImpl(this.initialDelay, this.period, this.retriedUpper,
                notification, messageRepository, paramConvertToolMap, invokeHandler);
        this.notification = notification;
    }

    /**
     * 启动守护任务
     */
    public void startProtectorTask() {
        protectorTask.startProtectorTask();
    }

    /**
     * 注册业务对应的
     * @param name 业务名称
     * @param paramConvertTool 参数与字符串转换工具
     * @throws ConsistencyException 工具重复注册异常
     */
    public void registerParamConvertTool(String name, ParamConvertTool paramConvertTool) throws ConsistencyException {
        if (paramConvertToolMap.containsKey(name)) {
            notification.noticeForException(ConsistencyException.CONSISTENCY_CONFLICTS_CONVERTTOOL);
            throw ConsistencyException.CONSISTENCY_CONFLICTS_CONVERTTOOL;
        }
        paramConvertToolMap.put(name, paramConvertTool);
    }

    /**
     * 查找注册好的参数转换工具
     * @param name 业务名称
     * @return 对应的参数字符串转换工具
     */
    public Optional<ParamConvertTool> getParamConvertTool(String name) {
        return Optional.ofNullable(paramConvertToolMap.get(name));
    }

    /**
     * 记录消息
     * @param messageId 消息编号必填项
     * @param name 业务名称
     * @param className 类名
     * @param method 方法名
     * @param param 参数
     */
    public void logMessage(String messageId, String name, String className, String method,
                           Object[] param, int period) throws ConsistencyException {
        Message message = new Message();
        message.setMessageId(messageId);
        message.setName(name);
        message.setClassName(className);
        message.setMethod(method);
        message.setParam(paramConvertToolMap.get(name).convertParamToStorage(param));
        message.setPeriod(period);
        messagePersistence.logMessage(message);
    }

    /**
     * 记录消息
     * @param messageId 消息编号必填项
     * @param name 业务名称
     * @param method 方法
     * @param param 参数
     * @param period 时间间隔
     */
    public void logMessage(String messageId, String name, Method method, Object[] param,
                           int period) throws ConsistencyException {
        Message message = new Message();
        message.setMessageId(messageId);
        message.setName(name);
        message.setClassName(method.getClass().getTypeName());
        message.setMethod(method.getName());
        message.setParam(paramConvertToolMap.get(name).convertParamToStorage(param));
        message.setPeriod(period);
        messagePersistence.logMessage(message);
    }
}
