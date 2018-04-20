package com.moseeker.consistencysuport.producer;

import com.moseeker.consistencysuport.common.MessageRepository;
import com.moseeker.consistencysuport.common.Notification;
import com.moseeker.consistencysuport.common.ParamConvertTool;
import com.moseeker.consistencysuport.consumer.Business;
import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.producer.echo.MessageChannel;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.notification.NotificationImpl;
import com.moseeker.consistencysuport.producer.persistence.MessagePersistence;
import com.moseeker.consistencysuport.producer.persistence.MessagePersistenceImpl;
import com.moseeker.consistencysuport.producer.protector.InvokeHandler;
import com.moseeker.consistencysuport.producer.protector.ProtectorTask;
import com.moseeker.consistencysuport.producer.protector.ProtectorTaskConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private MessageChannel messageChannel;

    private MessagePersistence messagePersistence;              //消息持久化工具
    private Map<String, ParamConvertTool> paramConvertToolMap;  //参数与持久化字段的转换工具
    private Notification notification;                          //通知功能
    private ProtectorTask protectorTask;                        //启动保护任务
    private InvokeHandler invokeHandler;

    private MessageTypeDetector messageTypeDetector;            //业务探针

    private long initialDelay = 5*1000;                         //任务延迟启动时间
    private long period = 5*60*1000;                            //守护任务时间间隔

    private long heartBeatTimeout = 2*60*60*1000;               //业务心跳超时时间

    private byte retriedUpper = 3;                              //重试次数上线

    private static final int MIN_PERIOD = 3*1000;               //时间间隔下限

    private ValidateBusiness validateBusiness;

    public ProducerConsistentManager(MessageRepository messageRepository,
                                     Map<String, ParamConvertTool> paramConvertToolMap, Notification notification,
                                     InvokeHandler invokeHandler,
                                     long initialDelay, long period, long heartBeatTimeout, byte retriedUpper,
                                     MessageChannel messageChannel, MessageTypeDetector messageTypeDetector) throws ConsistencyException {

        logger.debug("ProducerConsistentManager init...");

        this.initialDelay = initialDelay;
        this.period = period;
        this.retriedUpper = retriedUpper;
        this.heartBeatTimeout = heartBeatTimeout;

        if (period < MIN_PERIOD) {
            throw ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_PERIOD_ERROR;
        }
        if (notification == null) {
            notification = new NotificationImpl();
        }

        logger.debug("ProducerConsistentManager init messagePersistence");
        this.messagePersistence = new MessagePersistenceImpl(messageRepository);
        this.paramConvertToolMap = paramConvertToolMap;
        this.invokeHandler = invokeHandler;

        logger.debug("ProducerConsistentManager init protectorTask");
        this.protectorTask = new ProtectorTaskConfigImpl(this.initialDelay, this.period, this.retriedUpper,
                notification, messageRepository, paramConvertToolMap, this.invokeHandler);

        this.notification = notification;
        this.messageChannel = messageChannel;

        this.validateBusiness = new ValidateBusiness(initialDelay, period, heartBeatTimeout, notification, messageRepository);
        logger.debug("ProducerConsistentManager start ValidateBusiness");
        this.validateBusiness.startValidateBusinessTask();
        logger.debug("ProducerConsistentManager init finish");

        this.messageTypeDetector = messageTypeDetector;
    }

    /**
     * 初始化通道
     */
    public void initChannel() {
        if (messageChannel != null) {
            messageChannel.initChannel();
        }
    }

    /**
     * 启动守护任务
     */
    public void startProtectorTask() {
        if (protectorTask != null) {
            protectorTask.startProtectorTask();
        }
    }

    /**
     * 启动检查
     */
    public void startValidateBusiness() {
        if (validateBusiness != null) {
            validateBusiness.startValidateBusinessTask();
        }
    }

    /**
     * 初始化数据库
     */
    public void initDB() {
        if (messagePersistence != null) {
            messagePersistence.initDB();
        }
    }

    /**
     * 开启业务注册
     */
    public synchronized void registerMessageType() {
        List<MessageTypePojo> messageTypePojoList = messageTypeDetector.findMessageTypes();
        if (messageTypePojoList != null) {
            messagePersistence.registerMessageType(messageTypePojoList);
        }
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

    public void notification(ConsistencyException e) {
        notification.noticeForException(e);
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

    private void validateBusiness() {

    }
}
