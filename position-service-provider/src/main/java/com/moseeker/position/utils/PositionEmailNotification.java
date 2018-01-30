package com.moseeker.position.utils;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.email.Email;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PositionEmailNotification {

    Logger logger = LoggerFactory.getLogger(PositionEmailNotification.class);

    static List<String> devMails = new ArrayList<>();

    static String br = "<br/>";

    static {
        devMails = getEmails("position_sync.email.dev");
    }

    private static String getConfigString(String key) {
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
        String emailConfig = getConfigString(configKey);
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

    public void sendSyncFailureMail(ThirdPartyPositionForm form, IChannelType channel, Exception refreshException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【职位同步失败】");

            if(channel!=null) {
                ChannelType channelType = channel.getChannelType();
                titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            }

            StringBuilder messageBuilder = new StringBuilder();
            if(form!=null) {
                if(SyncRequestType.hasType(form.getRequestType()) ){
                    messageBuilder.append("【请求端】：").append(SyncRequestType.getInstance(form.getRequestType()).title()).append(br);
                }
                messageBuilder.append("【传送的json】：").append(JSON.toJSONString(form)).append(br);
            }

            messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(refreshException)).append(br);

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
                    logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    public void sendVerifyFailureMail(String data, IChannelType channel, Exception refreshException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【职位同步验证失败】");

            if(channel!=null) {
                ChannelType channelType = channel.getChannelType();
                titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            }

            StringBuilder messageBuilder = new StringBuilder();
            if(data!=null) {
                messageBuilder.append("【验证的相关信息】：").append(data).append(br);
            }

            messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(refreshException)).append(br);

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
                    logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    public void sendRefreshFailureMail(String message, IChannelType channel,Exception refreshException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【第三方渠道信息刷新失败】");

            if(channel!=null) {
                ChannelType channelType = channel.getChannelType();
                titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            }

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("【爬虫端传送的json】：").append(message).append(br);

            messageBuilder.append("【失败信息】:").append(refreshException.getMessage()).append(br);

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
                    logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    public static String getExceptionAllinformation(Exception ex){
        StringBuilder sb = new StringBuilder();
        sb.append(ex.toString());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sb.append( br+"at " + s );
        }
        return sb.toString();
    }
}
