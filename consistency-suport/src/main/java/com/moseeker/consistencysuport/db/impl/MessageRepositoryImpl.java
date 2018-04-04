package com.moseeker.consistencysuport.db.impl;

import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.db.consistencydb.tables.records.ConsistencyMessageRecord;
import org.jooq.impl.DefaultDSLContext;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jack on 03/04/2018.
 */
public class MessageRepositoryImpl implements MessageRepository {

    public MessageRepositoryImpl(DefaultDSLContext create) {
        this.create = create;
    }

    private static final String DATABASE = "consistencydb";

    private static final String CONSISTENCY_MESSAGE = "CREATE TABLE `consistency_message` (\n" +
            "  `message_id` varchar(64) NOT NULL COMMENT '消息编号',\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称，不允许重复',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本',\n" +
            "  `retry` tinyint(1) NOT NULL DEFAULT 0 COMMENT '重试次数',\n" +
            "  `last_retry_time` timestamp COMMENT '最后重试的时间',\n" +
            "  `param` varchar(3000) COMMENT '参数',\n" +
            "  `finish` tinyint(1) DEFAULT 0 COMMENT '是否完成, 0 未完成， 1 完成',\n" +
            "  `class_name` varchar(60) COMMENT '记录消息的类对象名称',\n" +
            "  `method` varchar(60) COMMENT '记录消息的类方法名称',\n" +
            "  `period` int(11) COMMENT '任务调度时间间隔  单位是秒',\n" +
            "  PRIMARY KEY (`message_id`),\n" +
            "  UNIQUE KEY `consistency_message_name` (`name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';";

    private static final String CONSISTENCY_BUSINESS = "CREATE TABLE `consistency_business` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            "  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '业务注册时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称',\n" +
            "  `finish` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否完成 0 未完成 1 完成',\n" +
            "  `last_shake_hand_time` timestamp COMMENT '最后握手的时间',\n" +
            "  `receive_email` varchar(120) NOT NULL COMMENT '接收告警邮件通知的邮箱',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `consistency_business_name` (`name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息业务表';";

    private DefaultDSLContext create;

    @Override
    public void initDatabase() {
        create.execute(String.format("create database if not exists `%s`", DATABASE));

        int messageCount = create.execute(String.format("select count(*) from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`= '%s' and `TABLE_NAME` = '%s'", DATABASE, "consistency_message"));
        if (messageCount == 0) {
            create.execute(CONSISTENCY_MESSAGE);
        }
        int businessCount = create.execute(String.format("select count(*) from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`= '%s' and `TABLE_NAME` = '%s'", DATABASE, "consistency_business"));
        if (businessCount == 0) {
            create.execute(CONSISTENCY_BUSINESS);
        }
    }

    @Override
    public Message fetchMessage(String messageId) {
        return null;
    }

    @Override
    public String saveMessage(Message message) {
        ConsistencyMessageRecord consistencyMessageRecord = new ConsistencyMessageRecord();
        consistencyMessageRecord.setMessageId(message.getMessageId());
        consistencyMessageRecord.setName(message.getName());
        consistencyMessageRecord.setParam(message.getParam());
        consistencyMessageRecord.setClassName(message.getClassName());
        consistencyMessageRecord.setMethod(message.getMethod());

        if (message.getCreateTime() > 0) {
            consistencyMessageRecord.setCreateTime(new Timestamp(message.getCreateTime()));
        }
        if (message.getUpdateTime() > 0) {
            consistencyMessageRecord.setUpdateTime(new Timestamp(message.getUpdateTime()));
        }
        if (message.getVersion() > 0) {
            consistencyMessageRecord.setVersion(message.getVersion());
        }
        if (message.getRetry() > 0) {
            consistencyMessageRecord.setRetry((byte) message.getRetry());
        }
        if (message.getLastRetryTime() > 0) {
            consistencyMessageRecord.setLastRetryTime(new Timestamp(message.getLastRetryTime()));
        }
        if (message.isFinish()) {
            consistencyMessageRecord.setFinish((byte) 1);
        }
        create.attach(consistencyMessageRecord);
        consistencyMessageRecord.insert();
        return consistencyMessageRecord.getMessageId();
    }

    @Override
    public int registerBusiness(String messageId, String name) {
        return 0;
    }

    @Override
    public List<Message> fetchUnFinishMessageBySpecifiedSecond(long second) {
        return null;
    }
}
