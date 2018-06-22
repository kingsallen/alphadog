package com.moseeker.position.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.email.Email;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.pojo.LiePinPositionVO;
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

    static List<String> liepinDevmails = new ArrayList<>();

    static String br = "<br/>";

    static {
        devMails = getEmails("position_sync.email.dev");
        liepinDevmails = getEmails("position_liepin_operation.email");
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

    /**
     * 发送职位同步失败邮件
     * @param form  页面填写的第三方职位信息
     * @param channel   渠道
     * @param syncException  错误异常
     */
    public void sendSyncFailureMail(ThirdPartyPositionForm form, IChannelType channel, Exception syncException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
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

            messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(syncException)).append(br);

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

    /**
     *  发送验证失败失败邮件
     * @param data  页面填写的第三方职位信息
     * @param channel   渠道
     * @param verifyException   错误异常
     */
    public void sendVerifyFailureMail(String data, IChannelType channel, Exception verifyException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
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

            messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(verifyException)).append(br);

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

    public <T> void sendUnMatchRedisJsonMail(Map<String,Object> notInOldDatas, Map<String,Object> notInNewDatas, ChannelType channelType) {
        String notInNewData = "";
        if(!notInNewDatas.isEmpty()){
            notInNewData = JSON.toJSONString(notInNewDatas);
        }
        String notInOldData = "";
        if(!notInOldDatas.isEmpty()) {
            notInOldData = JSON.toJSONString(notInOldDatas);
        }
        sendUnMatchMail(notInNewData,notInOldData,channelType,"【第三方redis缓存数据变动】");
    }

    public <T> void sendUnMatchOccupationMail(List<T> notInOldDatas,List<T> notInNewDatas, ChannelType channelType) {
        String notInNewData = "";
        if(!notInNewDatas.isEmpty()){
            notInNewData = formatJson(StructSerializer.toString(notInNewDatas));
        }
        String notInOldData = "";
        if(!notInOldData.isEmpty()) {
            notInOldData = formatJson(StructSerializer.toString(notInOldDatas));
        }
        sendUnMatchMail(notInNewData,notInOldData,channelType,"【第三方职位职能变动】");
    }

    public <T> void sendUnMatchMail(String notInNewData, String notInOldData, ChannelType channelType,String title) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append(title);

            if(channelType!=null) {
                titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            }

            StringBuilder messageBuilder = new StringBuilder();

            if(!StringUtils.isNullOrEmpty(notInNewData)) {
                messageBuilder.append("【本次删除】：").append(notInNewData).append(br);   //数据库存在，但本次刷新不存在
            }

            if(!StringUtils.isNullOrEmpty(notInOldData)) {
                messageBuilder.append("【本次新增】：").append(notInOldData).append(br);    //数据库不存在，但本次刷新存在
            }


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

    /**
     * 发送刷新失败的邮件
     * @param message   爬虫端推送的environ数据
     * @param channel   渠道号
     * @param refreshException  刷新异常
     */
    public void sendRefreshFailureMail(String message, IChannelType channel,Exception refreshException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
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

    /**
     * 对json字符串格式化输出
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        JSONArray array=JSON.parseArray(jsonStr);

        sb.append("<table border=1 bordercolor='#000000' style='border-collapse:collapse'>");
        for(int i=0;i<array.size();i++){
            if(i==0){
                sb.append("<tr>");
                for(Map.Entry<String,Object> entry:array.getJSONObject(i).entrySet()){
                    sb.append("<th>").append(entry.getKey()).append("</th>");
                }
                sb.append("</tr>");
            }
            sb.append("<tr>");
            for(Map.Entry<String,Object> entry:array.getJSONObject(i).entrySet()){
                sb.append("<td>").append(entry.getValue()).append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }


    /**
     * 发送猎聘同步失败邮件
     * @param
     * @author  cjm
     * @date  2018/6/22
     * @return
     */
    public void sendSyncLiepinFailEmail(LiePinPositionVO liePinPositionVO, Exception syncException, String ext){
            List<String> mails = liepinDevmails;
            if (mails == null || mails.size() == 0) {
                logger.warn("没有配置同步邮箱地址!");
                return;
            }

            try {

                Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

                StringBuilder titleBuilder = new StringBuilder();
                titleBuilder.append("【职位同步失败】");

                StringBuilder messageBuilder = new StringBuilder();
                if(liePinPositionVO!=null) {
                    messageBuilder.append("【传送的json】：").append(JSON.toJSONString(liePinPositionVO)).append(br);
                }

                if(syncException != null){
                    messageBuilder.append("【失败信息】:").append(getExceptionAllinformation(syncException)).append(br);
                }

                if(StringUtils.isNotNullOrEmpty(ext)){
                    messageBuilder.append("【其他信息】:").append(ext);
                }

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
}
