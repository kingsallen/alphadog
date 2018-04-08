package com.moseeker.consistencysuport.manager;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.util.List;
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

    private MessageHandler messageHandler;                      //消息持久化工具
    private Map<String, ParamConvertTool> paramConvertToolMap;  //参数与持久化字段的转换工具
    private List<String> warningEmailList;                      //报警通知接收人员
    private Notification notification;                          //通知功能

    private long period = 5*60*1000;                            //时间间隔

    private ThreadPool threadPool = ThreadPool.Instance;

    public ProducerConsistentManager(MessageHandler messageHandler,
                                     Map<String, ParamConvertTool> paramConvertToolMap, Notification notification) {
        this.messageHandler = messageHandler;
        this.paramConvertToolMap = paramConvertToolMap;
    }

    /**
     * 注册业务对应的
     * @param name
     * @param paramConvertTool
     * @throws ConsistencyException
     */
    public void registerParamConvertTool(String name, ParamConvertTool paramConvertTool) throws ConsistencyException {
        if (paramConvertToolMap.containsKey(name)) {
            throw ConsistencyException.CONSISTENCY_CONFLICTS_CONVERTTOOL;
        }
        paramConvertToolMap.put(name, paramConvertTool);
    }

    /**
     * 查找注册好的参数转换工具
     * @param name
     * @return
     */
    public Optional<ParamConvertTool> getParamConvertTool(String name) {

        return Optional.ofNullable(paramConvertToolMap.get(name));
    }

    /**
     * 记录消息
     * @param name
     * @param period
     */
    public void logMessage(String messageId, String name, String param, String className, String method, int period) throws ConsistencyException {
        messageHandler.logMessage(messageId, name, param, className, method, period);
    }
}
