package com.moseeker.consistencysuport.common;

import com.moseeker.consistencysuport.producer.MessageTypePojo;
import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

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
     * @return 业务编号 ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_BUSINESS_REGISTER_FAILED
     */
    String registerBusiness(String messageId, String name) throws ConsistencyException;

    /**
     * 查询距离指定时间之外还未完成的消息集合
     * @param second 时间范围
     * @return
     */
    List<Message> fetchUnFinishMessageBySpecifiedSecond(long second);

    /**
     * 更新消息的重试次数
     * @param messageList 需要被更新的消息
     * @throws ConsistencyException ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_RETRIED_FAILED
     */
    void updateRetried(List<Message> messageList) throws ConsistencyException;

    /**
     *
     * 更新消息业务完成
     *
     * @param messageId 消息编号
     * @param name 消息名称
     * @throws ConsistencyException ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_NOT_EXISTS
     */
    void finishBusiness(String messageId, String name) throws ConsistencyException;

    /**
     *
     *  更新心跳消息
     *
     * @param messageName  消息名称
     * @param businessName 业务名称
     * @throws ConsistencyException ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_TYPE_NOT_EXISTS
     */
    void heartBeat(String messageName, String businessName) throws ConsistencyException;

    /**
     * 将超过指定时间的消息业务类型的数据置为不可用
     * @param lostTime 时间
     * @throws
     */
    void disableBusinessTypeBySpecifiedShakeHandTime(long lostTime) throws ConsistencyException;

    /**
     * 注册消息类型
     * @param messageTypePojoList 消息类型集合
     */
    void registerMessageType(List<MessageTypePojo> messageTypePojoList);
}
