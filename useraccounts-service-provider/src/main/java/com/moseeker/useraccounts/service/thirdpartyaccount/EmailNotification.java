package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件通知工具
 * Created by jack on 27/09/2017.
 */
@Component
public class EmailNotification {
    @Autowired
    HrCompanyDao companyDao;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    static List<String> devMails = new ArrayList<>();

    static List<String> mails = new ArrayList<>();

    static String br = "<br/>";

    static {
        mails = getEmails("account_sync.email");
        devMails = getEmails("account_sync.email.dev");
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

    //发送同步失败的邮件
    public void sendWebBindFailureMail(List<String> mails, HrThirdPartyAccountDO thirdPartyAccount) {
        HrCompanyDO company = companyDao.getCompanyById(thirdPartyAccount.getCompanyId());

        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            ChannelType channelType = ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【第三方帐号绑定失败】");

            if (company != null) {
                titleBuilder.append(":【").append(company.getName()).append("】");
            }
            titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            titleBuilder.append(":【").append(thirdPartyAccount.getId()).append("】");

            StringBuilder messageBuilder = new StringBuilder();
            if (company != null) {
                messageBuilder.append("【所属公司】：").append(company.getName()).append(br);
            }
            messageBuilder.append("【第三方帐号ID】：").append(thirdPartyAccount.getId()).append(br);
            messageBuilder.append("【帐号名】：").append(thirdPartyAccount.getUsername()).append(br);
            appendExt(thirdPartyAccount,messageBuilder);

            if (StringUtils.isNotNullOrEmpty(thirdPartyAccount.getErrorMessage())) {
                messageBuilder.append("【失败信息】:").append(br);
                messageBuilder.append(EmojiFilter.unicodeToUtf8(thirdPartyAccount.getErrorMessage())).append(br);
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

    public void sendThirdPartyAccountExtHandlerFailureMail(List<String> mails, HrThirdPartyAccountDO accountDO, String message, HrCompanyDO company) {
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            ChannelType channelType = ChannelType.instaceFromInteger(accountDO.getChannel());

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【获取第三方渠道账号信息失败】");

            if (company != null) {
                titleBuilder.append(":【").append(company.getName()).append("】");
            }
            titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            titleBuilder.append(":【").append(accountDO.getId()).append("】");

            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("【第三方帐号ID】：").append(accountDO.getId()).append(br);
            messageBuilder.append("【帐号名】：").append(accountDO.getUsername()).append(br);
            appendExt(accountDO,messageBuilder);

            if (StringUtils.isNotNullOrEmpty(message)) {
                messageBuilder.append("【失败信息】:").append(br);
                messageBuilder.append(EmojiFilter.unicodeToUtf8(message)).append(br);
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
                    logger.error("发送获取第三方渠道账号新失败的失败原因邮件时，发生错误！错误信息：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送获取第三方渠道账号新失败的失败原因邮件时，发生错误！错误信息：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    public void sendFailureMail(List<String> mails, HrThirdPartyAccountDO thirdPartyAccount) {
        if (mails == null || mails.size() == 0) {
            logger.warn("没有配置同步邮箱地址!");
            return;
        }
        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【Sysplat插件绑定账号失败】");

            titleBuilder.append(":【").append(thirdPartyAccount.getId()).append("】");

            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("【第三方帐号ID】：").append(thirdPartyAccount.getId()).append(br);
            messageBuilder.append("【帐号名】：").append(thirdPartyAccount.getUsername()).append(br);
            appendExt(thirdPartyAccount,messageBuilder);

            if (StringUtils.isNotNullOrEmpty(thirdPartyAccount.getErrorMessage())) {
                messageBuilder.append("【失败信息】:").append(br);
                messageBuilder.append(EmojiFilter.unicodeToUtf8(thirdPartyAccount.getErrorMessage())).append(br);
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

    public void appendExt(HrThirdPartyAccountDO thirdPartyAccount,StringBuilder messageBuilder){
        ChannelType channelType=ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());

        switch (channelType){
            case JOB51:
                messageBuilder.append("【会员名】：").append(thirdPartyAccount.getExt()).append(br);
                break;
            case JOB1001:
                messageBuilder.append("【安全码】：").append(thirdPartyAccount.getExt()).append(br);
                break;
        }

    }

    public List<String> getDevMails() {
        return devMails;
    }

    public List<String> getMails() {
        return mails;
    }
}
