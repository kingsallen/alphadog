package com.moseeker.entity.biz;

import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.entity.pojo.resume.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 简历相关邮件发送工具
 *
 * @author cjm
 * @date 2018-06-26 11:08
 **/
@Component
public class ProfileMailUtil {

    static Logger logger = LoggerFactory.getLogger(ProfileMailUtil.class);

    final String br = "</br>";

    static List<String> profileParseDev;

    static {
            profileParseDev = getEmails("profile_parse_warn_email");
    }

    public void sendProfileParseWarnMail(Account account){
        sendProfileParseWarnMail(profileParseDev, account);
    }
    /**
     * 发送简历解析调用量预警邮件
     * @param
     * @author  cjm
     * @date  2018/6/22
     * @return
     */
    public void sendProfileParseWarnMail(List<String> mails, Account account){
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【ResumeSDK简历解析使用次数预警】");

            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("【可调用量共计】:").append(account.getUsage_limit()).append(br);
            messageBuilder.append("【剩余可调用量】:").append(account.getUsage_remaining()).append(br).append(br);
            messageBuilder.append("【注：每成功调用一次ResumeSDK简历解析，调用量减一，当剩余调用量为0时不能再调用。】");
            emailBuilder.setSubject(titleBuilder.toString());
            emailBuilder.setContent(messageBuilder.toString());
            if (mails.size() > 1) {
                emailBuilder.addCCList(mails.subList(1, mails.size()));
            }
            Email email = emailBuilder.build();
            email.send(3, new Email.EmailListener() {
                @Override
                public void success() {
                    logger.info("email send messageDelivered");
                }

                @Override
                public void failed(Exception e) {
                    logger.error("发送简历解析剩余调用量预警邮件时发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送简历解析剩余调用量预警邮件时发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    private static List<String> getEmails(String configKey) {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("setting.properties");
        } catch (Exception e) {
            logger.error("加载配置文件失败!");
            return new ArrayList<>();
        }

        String emailConfig = configUtils.get(configKey, String.class);
        if (emailConfig == null) {
            return new ArrayList<>();
        }

        String[] emailArrays = emailConfig.split(",");

        List<String> emails = new ArrayList<>();
        for (String s : emailArrays) {
            emails.add(s);
        }

        return emails;
    }
}
