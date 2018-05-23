package com.moseeker.chat.utils;

import com.moseeker.common.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送工具
 *
 * @author cjm
 * @date 2018-05-15 11:23
 **/
public class EmailSendUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailSendUtil.class);
    /**
     * @param
     * @return
     * @description 发送报警邮件
     * @author cjm
     * @date 2018/5/13
     */
    public static boolean sendWarnEmail(String emailAddress, String emailContent, String emailSubject) {
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(emailAddress);
            emailBuilder.setContent(emailContent);
            emailBuilder.setSubject(emailSubject);
            Email email = emailBuilder.build();
            email.send(3, new Email.EmailListener() {
                @Override
                public void success() {
                    logger.info("email send messageDelivered");
                }

                @Override
                public void failed(Exception e) {
                    logger.error("报警邮件发送失败：{}", e.getMessage());
                }
            });
            return true;
        } catch (Exception e) {
            logger.error("===============报警邮件发送失败，emailAddress:{}=================", emailAddress);
            e.printStackTrace();
        }
        return false;
    }
}
