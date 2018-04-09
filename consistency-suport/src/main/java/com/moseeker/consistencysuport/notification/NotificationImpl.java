package com.moseeker.consistencysuport.notification;

import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.FormCheck;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 通知的默认实现
 * 基于邮件通知。需要配置中包含 consistency_suport_error_emails 和 consistency_suport_exception_emails配置
 *
 * Created by jack on 04/04/2018.
 */
public class NotificationImpl implements Notification {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> errorReceivers = new ArrayList<>();            //程序异常报警接收邮件
    private List<String> exceptionReceives = new ArrayList<>();         //业务异常报警接收邮件

    public NotificationImpl() throws ConsistencyException {
        try {
            ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
            String errorReceivers = propertiesUtils.get("consistency_suport.error_emails", String.class);
            parseEmail(this.errorReceivers, errorReceivers, ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_ERROR_EMAIL);

            String exceptionReceives = propertiesUtils.get("consistency_suport.exception_emails", String.class);
            parseEmail(this.exceptionReceives, exceptionReceives, ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_EXCEPTION_EMAIL);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOTIFACATION_ERROR;
        }
    }

    /**
     * 解析邮箱
     * @param errorReceivers
     * @param emailStr
     */
    private void parseEmail(List<String> errorReceivers, String emailStr, ConsistencyException e) {
        if (StringUtils.isNotBlank(emailStr)) {
            String[] errorReceiversArray = emailStr.split(",");
            if (errorReceiversArray.length > 0) {
                for (String errorReceiver : errorReceiversArray) {
                    if (FormCheck.isEmail(errorReceiver)) {
                        errorReceivers.add(errorReceiver);
                    } else {
                        throw e;
                    }
                }
            }
        } else {
            throw e;
        }
    }

    @Override
    public void noticeForError(Exception e) {
        if (this.errorReceivers.size() == 0) {
            logger.error("没有配置程序错误报警邮件的接收人员邮箱！");
            return;
        }

        sendEmail(this.errorReceivers, e);
    }

    @Override
    public void noticeForException(ConsistencyException e) {

        if (this.exceptionReceives.size() == 0) {
            logger.error("没有配置业务异常报警邮件的接收人员邮箱！");
            return;
        }

        sendEmail(this.exceptionReceives, e);
    }

    /**
     * 发送错误邮件
     * @param receivers
     * @param e
     */
    private void sendEmail(List<String> receivers, Exception e) {
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(receivers);
            emailBuilder.setSubject(convertExceptionToString(e));
            Email email = emailBuilder.build();
            email.send();
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e);
        }
    }

    /**
     * 异常信息解析成字符串，用于邮件内容
     * @param e
     * @return
     */
    private String convertExceptionToString(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (StackTraceElement traceElement : stackTraceElements)
            stringBuffer.append("\tat " + traceElement);

        return stringBuffer.toString();
    }
}
