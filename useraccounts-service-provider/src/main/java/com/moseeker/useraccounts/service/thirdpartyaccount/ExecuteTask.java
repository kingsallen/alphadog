package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by zhangdi on 2017/8/11.
 */
public abstract class ExecuteTask {


    static Logger logger = LoggerFactory.getLogger(ExecuteTask.class);

    @Autowired
    HrCompanyDao companyDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    EmailNotification emailNotification;

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
        HrCompanyDO companyDO = companyDao.getCompanyById(hrThirdPartyAccount.getCompanyId());
        if (hrThirdPartyAccount.getBinding() == 6 || hrThirdPartyAccount.getBinding() == 7) {
            //发邮件cs
            emailNotification.sendFailureMail(emailNotification.getMails(), hrThirdPartyAccount, extras, companyDO);
        } else if (hrThirdPartyAccount.getBinding() != 1) {
            //发邮件dev
            emailNotification.sendFailureMail(emailNotification.getDevMails(), hrThirdPartyAccount, extras, companyDO);
        }
    }
}
