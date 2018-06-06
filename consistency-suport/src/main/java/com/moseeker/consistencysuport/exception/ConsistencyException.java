package com.moseeker.consistencysuport.exception;

import com.moseeker.common.exception.CommonException;

/**
 *
 * 一致性工具异常
 *
 * Created by jack on 03/04/2018.
 */
public class ConsistencyException extends CommonException {

    public ConsistencyException(int status, String message) {
        super(status, message);
    }

    public static final ConsistencyException CONSISTENCY_CONFLICTS_CONVERTTOOL = new ConsistencyException(81001, "重复的参数转换工具！");
    public static final ConsistencyException CONSISTENCY_UNBIND_CONVERTTOOL = new ConsistencyException(81002, "未找到参数转换工具！");
    public static final ConsistencyException CONSISTENCY_INVOKE_ERROR = new ConsistencyException(81003, "未找到消息重试处理组件！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_LOST_MESSAGEID = new ConsistencyException(81004, "消息编号配置错误！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_ERROR_EMAIL = new ConsistencyException(81005, "没有配置程序错误报警接收邮件！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_EXCEPTION_EMAIL = new ConsistencyException(81006, "没有配置业务错误报警接收邮件！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_CONFIGURATION_NOTIFACATION_ERROR = new ConsistencyException(81007, "消息处理工具构建失败！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_CONFIGURATION_REPOSITORY_NOT_FOUND= new ConsistencyException(81008, "消息持久化配置错误！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_CONFIGURATION_PERIOD_ERROR = new ConsistencyException(81009, "消息守护任务间隔时间太短！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_RETRY_OVER_LIMIT = new ConsistencyException(81010, "超过配置的重复次数上限！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_RETRIED_FAILED = new ConsistencyException(81011, "更新消息重试次数失败！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_MESSAGE_NOT_EXISTS = new ConsistencyException(81012, "消息不存在！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_MESSAGE_TYPE_NOT_EXISTS = new ConsistencyException(81014, "消息类型不存在！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_FINISH_RETRY_OVER_LIMIT = new ConsistencyException(81013, "将消息更新成完成状态时，重试次数超过上线！");
    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_MESSAGE_FINISH_PARAM_ERROR = new ConsistencyException(81017, "业务消息参数错误，更新完成消息失败！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_BUSINESS_REGISTER_FAILED = new ConsistencyException(81015, "业务消息注册失败！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_BUSINESS_REGISTER_PARAM_ERROR = new ConsistencyException(81016, "业务消息参数错误，注册失败！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_UPDATE_BUSINESS_HEART_BEAT_PARAM_ERROR = new ConsistencyException(81018, "业务消息参数错误，更新心跳时间失败！");

    public static final ConsistencyException CONSISTENCY_CONSUMER_LOST_MESSAGEID = new ConsistencyException(81019, "消息编号配置错误！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_MQ_CONFIG_LOST= new ConsistencyException(81020, "消息通道配置信息错误！");

    public static final ConsistencyException CONSISTENCY_CONSUMER_ANNOTATION_LOGT_CONFIG= new ConsistencyException(81021, "注解缺少必要信息！");

    public static final ConsistencyException CONSISTENCY_PRODUCER_PROTECTOR_PARAMETER_NOT_LEGAL = new ConsistencyException(81022, "消息参数错误！");
}