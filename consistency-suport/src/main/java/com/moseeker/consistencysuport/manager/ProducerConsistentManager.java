package com.moseeker.consistencysuport.manager;

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


    private void init() {

    }
}
