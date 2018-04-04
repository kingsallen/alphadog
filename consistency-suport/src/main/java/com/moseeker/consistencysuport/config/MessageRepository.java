package com.moseeker.consistencysuport.config;

import com.moseeker.consistencysuport.db.Message;

import java.util.List;

/**
 *
 * 提供发送消息持久化的相关操作
 *
 * Created by jack on 03/04/2018.
 */
public interface MessageRepository {

    /**
     * 初始化数据库
     * 1. create database if not exists `consistencydb`;
     * 2. select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`= 'consistencydb' and `TABLE_NAME` = 'consistency_message';
     * 3. create tables;
     */
    void initDatabase();

    /**
     * 根据消息编号查找消息
     * @param messageId 消息编号
     * @return 消息记录
     */
    Message fetchMessage(String messageId);

    /**
     *
     * 保存消息
     *
     * @param message 消息记录
     * @return
     */
    String saveMessage(Message message);


    /**
     * 想一个消息中注册业务
     * @param messageId 消息编号
     * @param name 业务名称
     * @return 业务编号
     */
    int registerBusiness(String messageId, String name);

    /**
     * 查询距离指定时间之外还未完成的消息集合
     * @param second
     * @return
     */
    List<Message> fetchUnFinishMessageBySpecifiedSecond(long second);
}
