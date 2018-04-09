package com.moseeker.consistencysuport.protector;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 轮训机制
 * todo 定时查找超时未完成的任务
 * todo 根据轮训次数执行消息发送或者报警
 * todo 可配置的定时处理
 *
 * Created by jack on 04/04/2018.
 */
public class ProtectorTaskConfigImpl implements ProtectorTask, Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Notification notification;              //通知
    private MessageRepository messageRepository;    //消息持久化操作
    private ApplicationContext applicationContext;  //Spring容器
    private Map<String, ParamConvertTool> paramConvertToolMap;      //参数转换工具

    private long period = 5*60*1000;                //时间间隔
    private long initialDelay = 3*1000;             //延迟启动

    ThreadPool threadPool = ThreadPool.Instance;

    public ProtectorTaskConfigImpl(long initialDelay, long period, Notification notification,
                                   MessageRepository messageRepository, Map<String, ParamConvertTool> paramConvertToolMap) {
        this.period = period;
        this.initialDelay = initialDelay;
        this.notification = notification;
        this.messageRepository = messageRepository;
        this.paramConvertToolMap = paramConvertToolMap;
    }

    // 定时任务
    private final static ScheduledThreadPoolExecutor schedual = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private AtomicInteger atoInteger = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ProtectorTaskConfig-Thread "+ atoInteger.getAndIncrement());
            return t;
        }
    });

    @Override
    public void startProtectorTask() {
        schedual.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    @Override
    public void reHandler(Message message) throws ConsistencyException {
        if (!message.isFinish()) {
            try {
                String className = message.getClassName();
                String methodName = message.getMethod();
                String params = message.getParam();
                Object object = applicationContext.getBean(className);
                if (object == null) {
                    throw ConsistencyException.CONSISTENCY_INVOKE_ERROR;
                }
                Class clazz = object.getClass();
                ParamConvertTool paramConvertTool = paramConvertToolMap.get(message.getName());
                if (paramConvertTool == null) {
                    throw ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL;
                }
                Object[] paramArray = paramConvertTool.convertStorageToParam(params);
                Class[] paramClassArray = null;
                List<Class> classList = null;
                if (paramArray != null && paramArray.length > 0) {
                    classList = new ArrayList<>();
                    for (Object param : paramArray) {
                        classList.add(param.getClass());
                    }
                    paramClassArray = new Class[classList.size()];
                    for (int i=0; i< classList.size(); i++) {
                        paramClassArray[i] = classList.get(i).getClass();
                    }
                }
                logger.debug("reHandler className:{}, methodName:{}, params:{}", className, methodName, params);
                Method method = clazz.getMethod(methodName, paramClassArray);
                if (method != null) {
                    method.invoke(object, paramArray);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                logger.error(e.getMessage(), e);
                throw ConsistencyException.CONSISTENCY_INVOKE_ERROR;
            }
        }
    }

    /**
     *
     * 查找未完成消息
     *
     * @return
     */
    private List<Message> fetchNotFinishedMessage() {

        long time = System.currentTimeMillis()-period;

        return messageRepository.fetchUnFinishMessageBySpecifiedSecond(time);
    }

    @Override
    public void run() {
        logger.debug("ProtectorTaskConfigImpl scheduleAtFixedRate time:{}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
        List<Message> messageList = fetchNotFinishedMessage();
        if (messageList != null && messageList.size() > 0) {
            messageList.forEach(message -> threadPool.startTast(() -> {
                        try {
                            reHandler(message);
                            return true;
                        } catch (ConsistencyException e) {
                            logger.error(e.getMessage(), e);
                            return false;
                        }
                    })
            );
        }
    }
}
