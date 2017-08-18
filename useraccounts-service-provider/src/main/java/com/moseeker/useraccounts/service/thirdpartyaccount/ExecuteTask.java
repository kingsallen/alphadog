package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/8/11.
 */
public abstract class ExecuteTask {


    static Logger logger = LoggerFactory.getLogger(ExecuteTask.class);

    static ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();

    static List<String> mails = new ArrayList<>();

    @Autowired
    HrCompanyDao companyDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;


    static {
        try {
            propertiesUtils.loadResource("setting.properties");
            String emailConfig = propertiesUtils.get("THIRD_PARTY_ACCOUNT_SYNC_EMAIL", String.class);

            if (StringUtils.isNotNullOrEmpty(emailConfig)) {

                String[] emailArrays = emailConfig.split(",");

                for (String s : emailArrays) {
                    mails.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 当前任务的名字
     *
     * @return
     */
    protected abstract String getTaskName();

    public abstract int getErrorCode(Exception e);

    /**
     * 请求chaos
     *
     * @return
     * @throws Exception
     */
    protected abstract HrThirdPartyAccountDO request(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception;

    public HrThirdPartyAccountDO execute(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        hrThirdPartyAccount = request(hrThirdPartyAccount, extras);
        hrThirdPartyAccount.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        hrThirdPartyAccount.setSyncTime(hrThirdPartyAccount.getUpdateTime());
        logger.info("Chaos回传解析成功:{}", JSON.toJSONString(hrThirdPartyAccount));

        return hrThirdPartyAccount;
    }

    /**
     * 更新第三方帐号到数据库，这里只更新时间和可发布数以及绑定状态
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public void updateThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) {
        int updateResult = hrThirdPartyAccountDao.updateData(hrThirdPartyAccount);

        if (updateResult < 1) {
            logger.error("更新第三方账号失败:{}", JSON.toJSONString(hrThirdPartyAccount));
            return;
        }
        if (hrThirdPartyAccount.getBinding() == 6 || hrThirdPartyAccount.getBinding() == 7) {
            sendFailureMail(hrThirdPartyAccount, extras);
        }
    }

    //发送同步失败的邮件
    public void sendFailureMail(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras) {
        logger.info("发送同步或刷新错误的邮件:syncType{}:thirdPartyAccount:{}", JSON.toJSONString(thirdPartyAccount));

        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            ChannelType channelType = ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【第三方帐号").append(getTaskName()).append("失败】");

            HrCompanyDO company = companyDao.getCompanyById(thirdPartyAccount.getCompanyId());
            if (company != null) {
                titleBuilder.append(":【").append(company.getName()).append("】");
            }
            titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            titleBuilder.append(":【").append(thirdPartyAccount.getId()).append("】");

            String br = "<br/>";

            StringBuilder messageBuilder = new StringBuilder();
            if (company != null) {
                messageBuilder.append("【所属公司】：").append(company.getName()).append(br);
            }
            messageBuilder.append("【第三方帐号ID】：").append(thirdPartyAccount.getId()).append(br);
            messageBuilder.append("【帐号名】：").append(thirdPartyAccount.getUsername()).append(br);
            if (StringUtils.isNotNullOrEmpty(thirdPartyAccount.getMembername())) {
                messageBuilder.append("【会员名】：").append(thirdPartyAccount.getMembername()).append(br);
            }

            if (extras != null && StringUtils.isNotNullOrEmpty(extras.get("company"))) {
                messageBuilder.append("【子公司简称】:").append(extras.get("company")).append(br);
            }

            if (StringUtils.isNotNullOrEmpty(thirdPartyAccount.getErrorMessage())) {
                messageBuilder.append("【失败信息】:").append(br);
                messageBuilder.append(thirdPartyAccount.getErrorMessage()).append(br);
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
