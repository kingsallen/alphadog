package com.moseeker.entity.biz;

import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
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

    private static Logger logger = LoggerFactory.getLogger(ProfileMailUtil.class);

    private static final String BR = "</br>";

    private static List<String> profileParseDev;

    private static List<String> devMails;

    private static String emailLevel;

    static {
        profileParseDev = getEmails("profile_parse_warn_email");
        devMails = getEmails("profile_mvhouse.email.dev");
        emailLevel = getConfigString("email.level");
    }
    /**
     * 简历搬家操作失败邮件发送
     * @param   mvHouseException 简历搬家失败的异常信息
     * @param   ext 额外信息
     * @author  cjm
     * @date  2018/7/23
     */
    public void sendMvHouseFailedEmail(Exception mvHouseException, String ext){
        sendMvHouseFailedEmail(devMails, mvHouseException, ext);
    }

    /**
     * 发送简历解析调用量预警邮件
     * @param account resumeSDK中的account参数
     * @author  cjm
     * @date  2018/6/22
     */
    public void sendProfileParseWarnMail(Account account){
        sendProfileParseWarnMail(profileParseDev, account);
    }

    public void sendMvHouseFailedEmail(List<String> mails, Exception mvHouseException, String ext){
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            String titleBuilder = "【"+ emailLevel +"】【简历搬家失败】";

            StringBuilder messageBuilder = new StringBuilder();

            if(mvHouseException != null){
                messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(mvHouseException)).append(BR);
            }

            if(StringUtils.isNotNullOrEmpty(ext)){
                messageBuilder.append("【其他信息】:").append(ext);
            }

            emailBuilder.setSubject(titleBuilder);
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
                    logger.error("发送同步职位到猎聘失败的邮件发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送同步职位到猎聘失败的邮件发生错误：{}", e.getMessage());
        }
    }

    private void sendProfileParseWarnMail(List<String> mails, Account account){
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            String title = "【ResumeSDK简历解析使用次数预警】";

            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("【可调用量共计】:").append(account.getUsage_limit()).append(BR);
            messageBuilder.append("【剩余可调用量】:").append(account.getUsage_remaining()).append(BR).append(BR);
            messageBuilder.append("【注：每成功调用一次ResumeSDK简历解析，调用量减一，当剩余调用量为0时不能再调用。】");
            emailBuilder.setSubject(title);
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
        String emailConfig = getConfigString(configKey);

        if(StringUtils.isNullOrEmpty(emailConfig)){
            return new ArrayList<>();
        }

        String[] emailArrays = emailConfig.split(",");

        return new ArrayList<>(Arrays.asList(emailArrays));
    }

    private static String getConfigString(String configKey){
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("setting.properties");
        } catch (Exception e) {
            logger.error("加载配置文件失败!");
            return null;
        }
        String emailConfig = configUtils.get(configKey, String.class);
        if (emailConfig == null) {
            return null;
        }
        return emailConfig;
    }

    private static String getExceptionAllinformation(Exception ex){
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(ex.toString());
        for (StackTraceElement s : trace) {
            sb.append(BR + "at ").append(s);
        }
        return sb.toString();
    }
}
