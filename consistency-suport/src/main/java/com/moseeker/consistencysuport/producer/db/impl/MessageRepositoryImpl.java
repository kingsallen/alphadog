package com.moseeker.consistencysuport.producer.db.impl;

import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.Constant;
import com.moseeker.consistencysuport.common.MessageRepository;
import com.moseeker.consistencysuport.constant.MessageState;
import com.moseeker.consistencysuport.producer.MessageTypePojo;
import com.moseeker.consistencysuport.producer.db.Business;
import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyBusiness;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyBusinessType;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyMessage;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyMessageType;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyBusinessRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyBusinessTypeRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyMessageRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyMessageTypeRecord;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.joda.time.DateTime;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * 消息持久化
 *
 * Created by jack on 03/04/2018.
 */
@Component
public class MessageRepositoryImpl implements MessageRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageRepositoryImpl() {}

    @Autowired
    DefaultDSLContext create;

    @Autowired
    DefaultConfiguration configuration;

    private static final String DATABASE = "consistencydb";

    private static final String CONSISTENCY_MESSAGE_TYPE = "CREATE TABLE `consistency_message_type` (\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称，不允许重复',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `class_name` varchar(60) COMMENT '记录消息的类对象名称',\n" +
            "  `method` varchar(60) COMMENT '记录消息的类方法名称',\n" +
            "  `period` int(11) COMMENT '任务调度时间间隔  单位是秒',\n" +
            "  PRIMARY KEY (`name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息类型表'";

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
            "  PRIMARY KEY (`message_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表'";

    private static final String CONSISTENCY_BUSINESS = "CREATE TABLE `consistency_business` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `name` varchar(20) NOT NULL COMMENT '业务名称',\n" +
            "  `finish` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否完成 0 未完成 1 完成',\n" +
            "  `message_id` varchar(64) NOT NULL COMMENT '消息编号',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `consistency_business_name` (`message_id`, `name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息业务表'";

    private static final String CONSISTENCY_BUSINESS_TYPE = "CREATE TABLE `consistency_business_type` (\n" +
            "\t`name` varchar(20) NOT NULL COMMENT '业务名称，不允许重复',\n" +
            "\t`message_name` varchar(20) NOT NULL COMMENT 'consistency_message.name',\n" +
            "\t`register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '业务注册时间',\n" +
            "\t`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "\t`last_shake_hand_time` timestamp COMMENT '最后握手的时间',\n" +
            "\t`receive_email` varchar(120) NOT NULL COMMENT '接收告警邮件通知的邮箱',\n" +
            "\t`enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否是正常的数据 0：表示逻辑删除，1表示正常数据',\n" +
            "\tPRIMARY KEY (`name`, `message_name`)\n" +
            ")ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息业务类型表'";

    @Transactional
    @Override
    public void initDatabase() {
        create.execute(String.format("create database if not exists `%s`", DATABASE));
        create.execute(String.format("use `%s`", DATABASE));
        int messageTypeCount = create.selectCount().from("INFORMATION_SCHEMA.TABLES").where("TABLE_SCHEMA = '"+DATABASE+"'").and("TABLE_NAME = 'consistency_message_type'").fetchOne().value1();
        if (messageTypeCount == 0) {
            create.execute(CONSISTENCY_MESSAGE_TYPE);
        }

        int messageCount = create.selectCount().from("INFORMATION_SCHEMA.TABLES").where("TABLE_SCHEMA = '"+DATABASE+"'").and("TABLE_NAME = 'consistency_message'").fetchOne().value1();
        if (messageCount == 0) {
            create.execute(CONSISTENCY_MESSAGE);
        }
        int businessCount = create.selectCount().from("INFORMATION_SCHEMA.TABLES").where("TABLE_SCHEMA = '"+DATABASE+"'").and("TABLE_NAME = 'consistency_business'").fetchOne().value1();
        if (businessCount == 0) {
            create.execute(CONSISTENCY_BUSINESS);
        }

        int businessTypeCount = create.selectCount().from("INFORMATION_SCHEMA.TABLES").where("TABLE_SCHEMA = '"+DATABASE+"'").and("TABLE_NAME = 'consistency_business_type'").fetchOne().value1();
        if (businessTypeCount == 0) {
            create.execute(CONSISTENCY_BUSINESS_TYPE);
        }
    }

    @Transactional
    @Override
    public Message fetchMessage(String messageId) {
        return null;
    }

    @Override
    public String saveMessage(Message message) {

        Connection connection = configuration.connectionProvider().acquire();
        logger.info("saveMessage connection : {}", connection.toString());
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

        String sql = create.insertInto(ConsistencyMessage.CONSISTENCY_MESSAGE)
                .columns(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID, ConsistencyMessage.CONSISTENCY_MESSAGE.NAME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.PARAM, ConsistencyMessage.CONSISTENCY_MESSAGE.CREATE_TIME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.UPDATE_TIME, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH)
                .values(message.getMessageId(), message.getName(), message.getParam(), createTime, updateTime, version, retried, finish)
                .onDuplicateKeyIgnore()
                .getSQL();
        logger.info("saveMessage  sql : {}", sql);
        int execute = create.insertInto(ConsistencyMessage.CONSISTENCY_MESSAGE)
                .columns(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID, ConsistencyMessage.CONSISTENCY_MESSAGE.NAME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.PARAM, ConsistencyMessage.CONSISTENCY_MESSAGE.CREATE_TIME,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.UPDATE_TIME, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION,
                        ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH)
                .values(message.getMessageId(), message.getName(), message.getParam(), createTime, updateTime, version, retried, finish)
                .onDuplicateKeyIgnore()
                .execute();
        logger.info("saveMessage  execute : {}", execute);
        //由于thrift调用方式会导致添加消息的动作落后于消息完成的动作，所以需要做一下兼容。消息完成时添加的消息是没有参数信息，需要此时做一下更新
        if (execute == 0) {
            create.update(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .set(ConsistencyMessage.CONSISTENCY_MESSAGE.PARAM, message.getParam())
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(message.getMessageId()))
                    .execute();
        }
        return message.getMessageId();
    }

    @Transactional
    @Override
    public String registerBusiness(String messageName, String businessName) throws ConsistencyException {
        ConsistencyMessageTypeRecord consistencyMessageTypeRecord = create
                .selectFrom(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                .where(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME.eq(messageName))
                .fetchOne();
        if (consistencyMessageTypeRecord == null) {
            throw ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_TYPE_NOT_EXISTS;
        }
        ConsistencyBusinessTypeRecord consistencyBusinessTypeRecord = create
                .selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                .where(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME.eq(businessName))
                .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME.eq(messageName))
                .fetchOne();
        if (consistencyBusinessTypeRecord == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            int count = create.insertInto(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                    .columns(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.REGISTER_TIME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.ENABLE)
                    .values(businessName, messageName, now, (byte)AbleFlag.ENABLE.getValue())
                    .onDuplicateKeyIgnore()
                    .execute();
            if (count ==  0) {
                throw ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_BUSINESS_REGISTER_FAILED;
            }
        }
        return consistencyBusinessTypeRecord.getName();
    }

    @Transactional
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

            consistencyMessageRecords.forEach(consistencyMessageRecord -> {
                Message message = recordToMessage(consistencyMessageRecord);
                messageList.add(message);
            });
        }
        return messageList;
    }

    @Transactional
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

    @Transactional
    @Override
    public void finishBusiness(String messageId, String messageName, String businessName) throws ConsistencyException {
        try {

            Result<ConsistencyMessageRecord> result4 = create.selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE).fetch();
            ConsistencyMessageTypeRecord consistencyMessageTypeRecord = create
                    .selectFrom(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                    .where(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME.eq(messageName))
                    .fetchOne();

            if (consistencyMessageTypeRecord == null) {
                throw ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_TYPE_NOT_EXISTS;
            }

            //由于thrift请求 是返回结果之后才会提交事务，所以导致添加消息不及时，添加消息可能会在完成任务之后才会进行，需要做兼容处理
            String sql = create
                    .selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(messageId))
                    .getSQL();
            logger.info("find message sql : " +sql);
            Result<ConsistencyBusinessRecord> result1 = create.selectFrom(ConsistencyBusiness.CONSISTENCY_BUSINESS).fetch();
            Result<ConsistencyBusinessTypeRecord> result2 = create.selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE).fetch();
            Result<ConsistencyMessageRecord> result = create.selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE).fetch();
            ConsistencyMessageRecord consistencyMessageRecord = create
                    .selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(messageId))
                    .fetchOne();
            if (consistencyMessageRecord == null) {
                create.insertInto(ConsistencyMessage.CONSISTENCY_MESSAGE)
                        .columns(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID,
                                ConsistencyMessage.CONSISTENCY_MESSAGE.NAME)
                        .values(messageId, messageName)
                        .onDuplicateKeyIgnore()
                        .execute();
            }
            logger.info("find consistency_message");
            ConsistencyBusinessTypeRecord consistencyBusinessTypeRecord = create
                    .selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                    .where(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME.eq(businessName))
                    .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME.eq(consistencyMessageRecord.getName()))
                    .fetchOne();
            logger.info("find consistency_business_type");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (consistencyBusinessTypeRecord == null) {
                create.insertInto(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                        .columns(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME,
                                ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME,
                                ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.REGISTER_TIME)
                        .values(businessName, consistencyMessageRecord.getName(), now)
                        .onDuplicateKeyIgnore();
            } else {
                if (consistencyBusinessTypeRecord.getEnable() == AbleFlag.DISABLE.getValue()) {
                    consistencyBusinessTypeRecord.setEnable((byte) AbleFlag.ENABLE.getValue());
                    create.attach(consistencyBusinessTypeRecord);
                    consistencyBusinessTypeRecord.update();
                }
            }

            logger.info("find consistency_business");
            ConsistencyBusinessRecord consistencyBusinessRecord = create
                    .selectFrom(ConsistencyBusiness.CONSISTENCY_BUSINESS)
                    .where(ConsistencyBusiness.CONSISTENCY_BUSINESS.MESSAGE_ID.eq(messageId))
                    .and(ConsistencyBusiness.CONSISTENCY_BUSINESS.NAME.eq(businessName))
                    .fetchOne();

            logger.info("find unfinished consistency_business");
            //查找正常业务未完成消息的数量。如果未查到任何信息，则表明该业务已经完全处理完毕
            Record1<Integer> countResult = create
                    .selectCount()
                    .from(ConsistencyBusiness.CONSISTENCY_BUSINESS)
                    .innerJoin(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                    .on(ConsistencyBusiness.CONSISTENCY_BUSINESS.NAME.eq(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME))
                    .where(ConsistencyBusiness.CONSISTENCY_BUSINESS.MESSAGE_ID.eq(messageId))
                    .and(ConsistencyBusiness.CONSISTENCY_BUSINESS.NAME.ne(businessName))
                    .and(ConsistencyBusiness.CONSISTENCY_BUSINESS.FINISH.eq(MessageState.UnFinish.getValue()))
                    .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.ENABLE.eq((byte) AbleFlag.ENABLE.getValue()))
                    .fetchOne();

            if (consistencyBusinessRecord == null) {
                create.insertInto(ConsistencyBusiness.CONSISTENCY_BUSINESS)
                        .columns(ConsistencyBusiness.CONSISTENCY_BUSINESS.MESSAGE_ID,
                                ConsistencyBusiness.CONSISTENCY_BUSINESS.NAME,
                                ConsistencyBusiness.CONSISTENCY_BUSINESS.FINISH)
                        .values(messageId, businessName, MessageState.Finish.getValue())
                        .onDuplicateKeyIgnore();
            } else {
                consistencyBusinessRecord.setFinish(MessageState.Finish.getValue());
                create.attach(consistencyBusinessRecord);
                consistencyBusinessRecord.update();
            }

            logger.info("find consistency_message");
            if (countResult.value1() == 0 && consistencyMessageRecord.getFinish() == MessageState.UnFinish.getValue()) {
                int execute = create.update(ConsistencyMessage.CONSISTENCY_MESSAGE)
                        .set(ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH, MessageState.Finish.getValue())
                        .set(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.add(1))
                        .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(messageId))
                        .and(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.eq(consistencyMessageRecord.getVersion()))
                        .execute();
                if (execute == 0) {
                    retryFinishMessage(messageId, 0);
                }
            }
        } catch (ConsistencyException | DataAccessException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    private void retryFinishMessage(String messageId, int index) throws ConsistencyException {
        if (index <= Constant.RETRY_UPPER) {
            index ++;
            ConsistencyMessageRecord consistencyMessageRecord = create
                    .selectFrom(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(messageId))
                    .fetchOne();
            if (consistencyMessageRecord == null) {
                throw ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_NOT_EXISTS;
            }
            int execute = create.update(ConsistencyMessage.CONSISTENCY_MESSAGE)
                    .set(ConsistencyMessage.CONSISTENCY_MESSAGE.FINISH, MessageState.Finish.getValue())
                    .set(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION, ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.add(1))
                    .where(ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID.eq(messageId))
                    .and(ConsistencyMessage.CONSISTENCY_MESSAGE.VERSION.eq(consistencyMessageRecord.getVersion()))
                    .execute();
            if (execute == 0) {
                retryFinishMessage(messageId, index);
            }
        } else {

            logger.error("finishBusiness over retry upper limit!  messageId : {}", messageId);
            throw ConsistencyException.CONSISTENCY_PRODUCER_RETRY_OVER_LIMIT;
        }
    }

    @Transactional
    @Override
    public void heartBeat(String messageName, String businessName) throws ConsistencyException {
        ConsistencyMessageTypeRecord consistencyMessageTypeRecord = create
                .selectFrom(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                .where(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME.eq(messageName))
                .fetchOne();
        if (consistencyMessageTypeRecord == null) {
            throw ConsistencyException.CONSISTENCY_PRODUCER_MESSAGE_TYPE_NOT_EXISTS;
        }
        ConsistencyBusinessTypeRecord consistencyBusinessTypeRecord = create
                .selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                .where(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME.eq(businessName))
                .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME
                        .eq(messageName))
                .fetchOne();
        if (consistencyBusinessTypeRecord == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            int count = create.insertInto(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                    .columns(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.REGISTER_TIME,
                            ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.LAST_SHAKE_HAND_TIME)
                    .values(businessName, messageName, now, now)
                    .onDuplicateKeyIgnore()
                    .execute();
            if (count == 0) {
                updateLastShakeHandTime(messageName, businessName);
            }
        } else {
            updateLastShakeHandTime(messageName, businessName);
        }
    }

    @Transactional
    @Override
    public void disableBusinessTypeBySpecifiedShakeHandTime(long lostTime) throws ConsistencyException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - lostTime);
        List<ConsistencyBusinessTypeRecord> businessTypeRecordList =
                create.selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                .where(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.ENABLE.eq((byte) AbleFlag.ENABLE.getValue()))
                .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.LAST_SHAKE_HAND_TIME.lt(timestamp))
                .fetch();
        if (businessTypeRecordList != null && businessTypeRecordList.size() > 0) {
            businessTypeRecordList.forEach(consistencyBusinessTypeRecord -> {
                logger.info("disableBusinessTypeBySpecifiedShakeHandTime consistencyBusinessTypeRecord name:{}, " +
                        "message_name:{}, time:{}",
                        consistencyBusinessTypeRecord.getName(),
                        consistencyBusinessTypeRecord.getMessageName(),
                        new DateTime().toString("YYYY-MM-dd HH:mm:ss"));
                consistencyBusinessTypeRecord.setEnable((byte) AbleFlag.DISABLE.getValue());
                create.attach(consistencyBusinessTypeRecord);
                consistencyBusinessTypeRecord.update();
            });
        }
    }

    @Transactional
    @Override
    public void registerMessageType(List<MessageTypePojo> messageTypePojoList) {
        if (messageTypePojoList != null) {
            messageTypePojoList.forEach(messageTypePojo -> {
                create.insertInto(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE)
                        .columns(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME,
                                ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.CLASS_NAME,
                                ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.METHOD)
                        .values(messageTypePojo.getName(), messageTypePojo.getClassName(), messageTypePojo.getMethod())
                        .onDuplicateKeyIgnore()
                        .execute();
            });
        }
    }

    /**
     * 更新心跳时间
     * @param messageName 消息名称
     * @param name 业务名称
     */
    private void updateLastShakeHandTime(String messageName, String name) {
        ConsistencyBusinessTypeRecord consistencyBusinessTypeRecord = create
                .selectFrom(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE)
                .where(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME.eq(name))
                .and(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME
                        .eq(messageName))
                .fetchOne();
        if (consistencyBusinessTypeRecord != null) {
            consistencyBusinessTypeRecord.setLastShakeHandTime(new Timestamp(System.currentTimeMillis()));
            consistencyBusinessTypeRecord.setEnable((byte) AbleFlag.ENABLE.getValue());
            create.attach(consistencyBusinessTypeRecord);
            consistencyBusinessTypeRecord.update();
        }
    }

    private void retryLockedRecord(List<Message> needRetry, int retried) throws ConsistencyException {
        if (retried < Constant.RETRY_UPPER) {
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
                                .set(ConsistencyMessage.CONSISTENCY_MESSAGE.RETRIED, consistencyMessageRecord.getRetried())
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
     * @param optional 业务类型
     * @return 业务
     */
    private Business recordToBusiness(ConsistencyBusinessRecord consistencyBusinessRecord, Optional<ConsistencyBusinessTypeRecord> optional) {
        if (!optional.isPresent() || optional.get().getEnable() == 0) {
            return null;
        }
        Business business = new Business();
        business.setId(consistencyBusinessRecord.getId());
        business.setName(consistencyBusinessRecord.getName());

        business.setRegisterTime(optional.get().getRegisterTime().getTime());
        if (optional.get().getLastShakeHandTime() != null) {
            business.setLastShakeHandTime(optional.get().getLastShakeHandTime().getTime());
        }

        if (consistencyBusinessRecord.getUpdateTime() != null) {
            business.setUpdateTime(consistencyBusinessRecord.getUpdateTime().getTime());
        }
        business.setFinish(consistencyBusinessRecord.getFinish() == 1);

        business.setMessageId(consistencyBusinessRecord.getMessageId());
        return business;
    }

    /**
     * jooq onsistencyMessageRecord 转 Message
     * @param consistencyMessageRecord 消息体
     * @return 消息
     */
    private Message recordToMessage(ConsistencyMessageRecord consistencyMessageRecord) {
        Message message = new Message();
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
        return message;
    }
}
