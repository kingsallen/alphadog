package com.moseeker.consistencysuport.db.impl;

import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.consistencysuport.db.Business;
import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.db.consistencydb.tables.ConsistencyBusiness;
import com.moseeker.consistencysuport.db.consistencydb.tables.ConsistencyMessage;
import com.moseeker.consistencysuport.db.consistencydb.tables.ConsistencyMessageType;
import com.moseeker.consistencysuport.db.consistencydb.tables.records.ConsistencyBusinessRecord;
import com.moseeker.consistencysuport.db.consistencydb.tables.records.ConsistencyMessageRecord;
import com.moseeker.consistencysuport.db.consistencydb.tables.records.ConsistencyMessageTypeRecord;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by jack on 03/04/2018.
 */
public class MessageRepositoryImpl implements MessageRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPool threadPool = ThreadPool.Instance;

    public MessageRepositoryImpl(DefaultDSLContext create) {
        this.create = create;
    }

    private static final String DATABASE = "consistencydb";

    private static final String CONSISTENCY_MESSAGE_TYPE = "CREATE TABLE `consistency_message_type` (\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称，不允许重复',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `class_name` varchar(60) COMMENT '记录消息的类对象名称',\n" +
            "  `method` varchar(60) COMMENT '记录消息的类方法名称',\n" +
            "  PRIMARY KEY (`name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息类型表';";

    private static final String CONSISTENCY_MESSAGE = "CREATE TABLE `consistency_message` (\n" +
            "  `message_id` varchar(64) NOT NULL COMMENT '消息编号',\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本',\n" +
            "  `retried` tinyint(1) NOT NULL DEFAULT 0 COMMENT '已经重试的次数',\n" +
            "  `last_retry_time` timestamp COMMENT '最后重试的时间',\n" +
            "  `param` varchar(3000) COMMENT '参数',\n" +
            "  `finish` tinyint(1) DEFAULT 0 COMMENT '是否完成, 0 未完成， 1 完成',\n" +
            "  `period` int(11) COMMENT '任务调度时间间隔  单位是秒',\n" +
            "  PRIMARY KEY (`message_id`)\n" +
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

        int messageTypeCount = create.execute(String.format("select count(*) from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`= '%s' and `TABLE_NAME` = '%s'", DATABASE, "consistency_message_type"));
        if (messageTypeCount == 0) {
            create.execute(CONSISTENCY_MESSAGE_TYPE);
        }

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

        create.insertInto(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                .columns(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME,
                        ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.CLASS_NAME,
                        ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.METHOD,
                        ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.PERIOD)
                .values(message.getName(), message.getClassName(), message.getMethod(), message.getPeriod())
                .onDuplicateKeyUpdate()
                .set(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME, message.getName())
                .execute();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp createTime;
        if (message.getCreateTime() > 0) {
            createTime = new Timestamp(message.getCreateTime());
        } else {
            createTime = now;
        }
        Timestamp updateTime;
        if (message.getUpdateTime() > 0) {
            updateTime = new Timestamp(message.getUpdateTime());
        } else {
            updateTime = now;
        }
        int version = 0;
        if (message.getVersion() > 0) {
            version = message.getVersion();
        }
        byte retried = 0;
        if (message.getRetried() > 0) {
            retried = (byte) message.getRetried();
        }
        byte finish = 0;
        if (message.isFinish()) {
            finish = 1;
        }
        create.insertInto(ConsistencyMessage.CONSISTENCY_MESSAGE)
                .columns(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID, ConsistencyMessage.CONSISTENCY_MESSAGE.NAME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.PARAM, ConsistencyMessage.CONSISTENCY_MESSAGE.CREATE_TIME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.UPDATE_TIME, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH)
                .values(message.getMessageId(), message.getName(), message.getParam(), createTime, updateTime, version, retried, finish)
                .onDuplicateKeyUpdate()
                .set(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID, message.getMessageId())
                .execute();
        return message.getMessageId();
    }

    @Override
    public int registerBusiness(String messageId, String name) {
        return 0;
    }

    @Override
    public List<Message> fetchUnFinishMessageBySpecifiedSecond(long second) {

        List<Message> messageList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(second);
        Result<ConsistencyMessageRecord> consistencyMessageRecords =
                create.selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE)
                .where(ConsistencyMessage.CONSISTENCY_MESSAGE.LAST_RETRY_TIME.lt(timestamp))
                .and(ConsistencyMessage.CONSISTENCY_MESSAGE.LAST_RETRY_TIME.lt(timestamp))
                .and(ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH.eq((byte) 0))
                .fetch();

        if (consistencyMessageRecords != null && consistencyMessageRecords.size() > 0) {

            Future<List<ConsistencyBusinessRecord>> businessFuture = threadPool.startTast(() -> {
                List<String> messageIdList = consistencyMessageRecords
                        .stream()
                        .map(ConsistencyMessageRecord::getMessageId)
                        .collect(Collectors.toList());

                return (List<ConsistencyBusinessRecord>) create.selectFrom(ConsistencyBusiness.CONSISTENCY_BUSINESS)
                                .where(ConsistencyBusiness.CONSISTENCY_BUSINESS.MESSAGE_ID.in(messageIdList))
                                .and(ConsistencyBusiness.CONSISTENCY_BUSINESS.ENABLE.eq((byte) AbleFlag.ENABLE.getValue()))
                                .fetch();
            });


            Future<List<ConsistencyMessageTypeRecord>> messageTypeFuture = threadPool.startTast(() -> {
                List<String> nameList = consistencyMessageRecords
                        .stream()
                        .map(ConsistencyMessageRecord::getName)
                        .collect(Collectors.toList());
                return (List<ConsistencyMessageTypeRecord>) create.selectFrom(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                        .where(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME.in(nameList))
                        .fetch();
            });


            consistencyMessageRecords.forEach(consistencyMessageRecord -> {

                try {
                    List<ConsistencyBusinessRecord> businessRecordResult = businessFuture.get();
                    List<ConsistencyMessageTypeRecord> messageTypeRecordResult = messageTypeFuture.get();

                    List<ConsistencyBusinessRecord> recordList = businessRecordResult
                            .stream()
                            .filter(consistencyBusinessRecord
                                    -> consistencyBusinessRecord.getMessageId().equals(consistencyMessageRecord.getMessageId()))
                            .collect(Collectors.toList());
                    if (recordList != null && recordList.size() > 0) {

                        List<Business> businessList = new ArrayList<>();
                        recordList.forEach(consistencyBusinessRecord -> {
                            Business business = recordToBusiness(consistencyBusinessRecord);
                            businessList.add(business);
                        });
                        Message message = recordToMessage(consistencyMessageRecord, businessList, messageTypeRecordResult);
                        messageList.add(message);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        return messageList;
    }

    @Override
    public void updateRetried(List<Message> messageList) throws ConsistencyException {
        List<Message> needRetry = new ArrayList<>();
        messageList.forEach(message -> {
            int execute = create.update(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .set(ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, (byte)message.getRetried())
                    .set(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.add(1))
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(message.getMessageId()))
                    .and(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.eq(message.getVersion()))
                    .execute();
            if (execute == 0) {
                needRetry.add(message);
            }
        });
        retryLockedRecord(needRetry, 0);
    }

    private void retryLockedRecord(List<Message> needRetry, int retried) throws ConsistencyException {
        if (retried < 3) {
            List<String> messageIdList = needRetry.stream().map(Message::getMessageId).collect(Collectors.toList());
            if (messageIdList != null && messageIdList.size() > 0) {
                List<ConsistencyMessageRecord> consistencyMessageRecordList = create
                        .selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE)
                        .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.in(messageIdList))
                        .fetch();
                if (consistencyMessageRecordList != null && consistencyMessageRecordList.size() > 0) {
                    List<Message> messageList = new ArrayList<>();
                    consistencyMessageRecordList.forEach(consistencyMessageRecord -> {
                        int execute = create.update(ConsistencyMessage.CONSISTENCY_MESSAGE)
                                .set(ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, (byte)consistencyMessageRecord.getRetried())
                                .set(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.add(1))
                                .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(consistencyMessageRecord.getMessageId()))
                                .and(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.eq(consistencyMessageRecord.getVersion()))
                                .execute();
                        if (execute == 0) {
                            Message message = new Message();
                            message.setMessageId(consistencyMessageRecord.getMessageId());
                            message.setName(consistencyMessageRecord.getName());
                            message.setRetried(consistencyMessageRecord.getRetried());
                            messageList.add(message);
                        }
                    });
                    if (messageIdList.size() > 0) {
                        retryLockedRecord(messageList, retried+1);
                    }
                }
            }

        } else {
            logger.error("超过重试次数上线");
            throw ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_RETRIED_FAILED;
        }
    }

    /**
     *
     * jooq ConsistencyBusinessRecord 转 Business
     *
     * @param consistencyBusinessRecord 注册的业务
     * @return 业务
     */
    private Business recordToBusiness(ConsistencyBusinessRecord consistencyBusinessRecord) {
        Business business = new Business();
        business.setId(consistencyBusinessRecord.getId());
        business.setName(consistencyBusinessRecord.getName());
        business.setRegisterTime(consistencyBusinessRecord.getRegisterTime().getTime());
        if (consistencyBusinessRecord.getUpdateTime() != null) {
            business.setUpdateTime(consistencyBusinessRecord.getUpdateTime().getTime());
        }
        business.setFinish(consistencyBusinessRecord.getFinish() == 1);
        if (consistencyBusinessRecord.getLastShakeHandTime() != null) {
            business.setLastShakeHandTime(consistencyBusinessRecord.getLastShakeHandTime().getTime());
        }
        business.setMessageId(consistencyBusinessRecord.getMessageId());
        return business;
    }

    /**
     * jooq onsistencyMessageRecord 转 Message
     * @param consistencyMessageRecord 消息体
     * @param businessList  注册的业务
     * @param messageTypeRecordList 消息类型
     * @return 消息
     */
    private Message recordToMessage(ConsistencyMessageRecord consistencyMessageRecord, List<Business> businessList,
                                    List<ConsistencyMessageTypeRecord> messageTypeRecordList) {
        Message message = new Message();
        message.setBusinessList(businessList);
        message.setName(consistencyMessageRecord.getName());
        message.setMessageId(consistencyMessageRecord.getMessageId());
        message.setCreateTime(consistencyMessageRecord.getCreateTime().getTime());
        if (consistencyMessageRecord.getUpdateTime() != null) {
            message.setUpdateTime(consistencyMessageRecord.getUpdateTime().getTime());
        }
        message.setVersion(consistencyMessageRecord.getVersion());
        message.setRetried(consistencyMessageRecord.getRetried());
        if (consistencyMessageRecord.getLastRetryTime() != null) {
            message.setLastRetryTime(consistencyMessageRecord.getLastRetryTime().getTime());
        }
        message.setParam(consistencyMessageRecord.getParam());
        message.setFinish(consistencyMessageRecord.getFinish() == 1);

        if (messageTypeRecordList != null) {
            Optional<ConsistencyMessageTypeRecord> optional = messageTypeRecordList
                    .stream()
                    .filter(consistencyMessageTypeRecord
                            -> consistencyMessageTypeRecord.getName().equals(consistencyMessageRecord.getName()))
                    .findAny();
            if (optional.isPresent()) {
                message.setClassName(optional.get().getClassName());
                message.setMethod(optional.get().getMethod());
                message.setPeriod(optional.get().getPeriod());
            }
        }

        return message;
    }
}
