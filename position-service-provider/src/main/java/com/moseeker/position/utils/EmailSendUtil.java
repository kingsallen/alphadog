package com.moseeker.position.utils;

import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EmailSendUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailSendUtil.class);

    private static List<String> mails = new ArrayList<>();

    static{
        mails = getEmails("position_liepin_operation.email");
    }

    private static String getConfig(String key) {
        try {
            ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
            configUtils.loadResource("setting.properties");
            return configUtils.get(key, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getEmails(String configKey) {
        String confitStr = getConfig(configKey);
        if (confitStr == null) {
            return new ArrayList<>();
        }

        String[] emailArrays = confitStr.split(",");

        List<String> emails = new ArrayList<>();


        for (String s : emailArrays) {
            emails.add(s);
        }

        return emails;
    }
    /**
     * 发送报警邮件
     * @param emailContent 邮件内容
     * @param emailSubject 邮件标题
     * @return 发送结果
     * @author cjm
     * @date 2018/6/18
     */
    public static boolean sendWarnEmail(String emailContent, String emailSubject) {
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails);
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
                    logger.error("猎聘api通知邮件发送失败：{}", e.getMessage());
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
