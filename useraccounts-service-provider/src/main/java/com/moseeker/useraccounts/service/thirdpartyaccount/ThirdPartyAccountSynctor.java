package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangdi on 2017/6/16.
 */

@Service
public class ThirdPartyAccountSynctor {

    static Logger logger = LoggerFactory.getLogger(ThirdPartyAccountSynctor.class);

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);


    static ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();

    static String csEmail;

    static {
        try {
            propertiesUtils.loadResource("setting.properties");
            csEmail = propertiesUtils.get("THIRD_PARTY_ACCOUNT_SYNC_EMAIL", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;


    /**
     * 第三方帐号异步绑定和刷新的实现
     */
    class ThirdPartyAccountSyncTask implements Runnable {

        HrThirdPartyAccountDO hrThirdPartyAccount;

        int syncType; //绑定0,刷新1

        ThirdPartyAccountSyncTask(int syncType, HrThirdPartyAccountDO hrThirdPartyAccount) {
            this.syncType = syncType;
            this.hrThirdPartyAccount = hrThirdPartyAccount;
        }

        @Override
        public void run() {
            try {
                HrThirdPartyAccountDO result = syncType == 0 ? chaosService.binding(hrThirdPartyAccount) : chaosService.synchronization(hrThirdPartyAccount);
                //刷新成功

                //更新到数据库
                result.setBinding(Short.valueOf("1"));
                result.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                result.setSyncTime(result.getUpdateTime());

                logger.info("Chaos回传解析成功:{}:{}", syncType, JSON.toJSONString(result));
                int updateResult = updateThirdPartyAccount(result);
                logger.info("Chaos回传解析成功 更改数据信息:{}:更改状态:{}", syncType, updateResult);
                if (updateResult < 1) {
                    logger.warn("Chaos回传解析成功 更改数据信息失败:{}:更改状态:{}", syncType, updateResult);
                    throw new BIZException(-1, "同步成功，但无法保存到数据库");
                }
            } catch (BIZException e) {
                if (e.getCode() == 1) {
                    //帐号密码错误，将状态改为5
                    hrThirdPartyAccount.setBinding(Short.valueOf("5"));
                    int updateResult = updateThirdPartyAccount(hrThirdPartyAccount);

                    if (updateResult < 1) {
                        //更新失败，发送邮件
                        sendFailureMail(syncType, hrThirdPartyAccount, "密码错误，但无法更新到数据库");
                    }
                } else {
                    //Chaos那边的其它异常，发送邮件
                    sendFailureMail(syncType, hrThirdPartyAccount, e.getMessage());
                }
            } catch (Exception e) {
                //系统的异常
                sendFailureMail(syncType, hrThirdPartyAccount, "系统异常：" + e.getMessage() == null ? "" : e.getMessage());
            }
        }
    }

    //发送同步失败的邮件
    private void sendFailureMail(int syncType, HrThirdPartyAccountDO thirdPartyAccount, String message) {
        logger.info("发送同步错误的邮件:syncType{}:thirdPartyAccount:{}:message:{}", syncType, JSON.toJSONString(thirdPartyAccount), message);

        if (csEmail == null) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(csEmail);

            String channelName = thirdPartyAccount.getChannel() == 1 ? "51JOB" :
                    thirdPartyAccount.getChannel() == 2 ? "猎聘" :
                            thirdPartyAccount.getChannel() == 3 ? "智联" : String.valueOf(thirdPartyAccount.getChannel());

            String content = new StringBuilder()
                    .append("这是一条")
                    .append(syncType == 0 ? "绑定" : "同步")
                    .append(channelName)
                    .append("帐号失败的消息")
                    .append("<br/>")
                    .append("用户名:").append("<br/>")
                    .append("ID:").append(thirdPartyAccount.getId()).append("<br/>")
                    .append("帐号:").append(thirdPartyAccount.getUsername()).append("<br/>")
                    .append("会员名:").append(thirdPartyAccount.getMembername() == null ? "" : thirdPartyAccount.getMembername()).append("<br/>")
                    .append("失败信息:").append("<br/>")
                    .append(message == null ? "" : message)
                    .toString();

            emailBuilder.setSubject("【" + channelName + "】【账号" + (syncType == 0 ? "绑定" : "刷新") + "失败】");
            emailBuilder.setContent(content);
            emailBuilder.build().send();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 绑定第三方帐号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @param async
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO bindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, boolean async) throws Exception {
        return async ? asyncWithBindThirdPartyAccount(hrId, thirdPartyAccount) : syncWithBindThirdPartyAccount(hrId, thirdPartyAccount);
    }

    /**
     * 使用异步的方式去同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        //先保存信息到数据库,状态为2绑定中
        thirdPartyAccount.setBinding(Short.valueOf("2"));
        HrThirdPartyAccountDO hrThirdPartyAccount = hrThirdPartyAccountDao.addThirdPartyAccount(hrId, thirdPartyAccount);
        //开启线程后台取处理第三方账号同步
        new Thread(new ThirdPartyAccountSyncTask(0, hrThirdPartyAccount)).start();
        return hrThirdPartyAccount;
    }


    /**
     * 使用同步的方式同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        //先绑定
        HrThirdPartyAccountDO bindResult = chaosService.binding(thirdPartyAccount);
        //绑定成功之后添加到数据库
        bindResult = hrThirdPartyAccountDao.addThirdPartyAccount(hrId, bindResult);
        return bindResult;
    }


    /**
     * 同步第三方帐号的可发布数等
     *
     * @param thirdPartyAccount
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO syncThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount, boolean async) throws Exception {
        return async ? asyncWithSyncThirdPartyAccount(thirdPartyAccount) : syncWithSyncThirdPartyAccount(thirdPartyAccount);
    }

    /**
     * 异步的方式
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount) throws Exception {
        //先更新数据库的状态为刷新中3
        hrThirdPartyAccount.setBinding(Short.valueOf("3"));
        int updateResult = updateThirdPartyAccount(hrThirdPartyAccount);
        if (updateResult < 1) {
            //更新失败
            throw new BIZException(-1, "系统异常,请重试");
        }

        new Thread(new ThirdPartyAccountSyncTask(1, hrThirdPartyAccount)).start();

        return hrThirdPartyAccount;
    }

    /**
     * 同步的方式
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount) throws Exception {
        HrThirdPartyAccountDO syncResult = chaosService.synchronization(hrThirdPartyAccount);
        //更新回数据库
        syncResult.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        syncResult.setSyncTime(syncResult.getUpdateTime());
        int updateResult = updateThirdPartyAccount(syncResult);
        if (updateResult < 1) {
            //更新失败
            throw new BIZException(-1, "系统异常,请重试");
        }
        return syncResult;
    }

    /**
     * 更新第三方帐号到数据库，这里只更新时间和可发布数以及绑定状态
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private int updateThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount) {
        HrThirdPartyAccountDO newThirdPartyAccount = new HrThirdPartyAccountDO();
        newThirdPartyAccount.setId(hrThirdPartyAccount.getId());
        newThirdPartyAccount.setBinding(hrThirdPartyAccount.getBinding());
        newThirdPartyAccount.setSyncTime(hrThirdPartyAccount.getSyncTime());
        newThirdPartyAccount.setUpdateTime(hrThirdPartyAccount.getUpdateTime());
        newThirdPartyAccount.setRemainNum(hrThirdPartyAccount.getRemainNum());
        newThirdPartyAccount.setRemainProfileNum(hrThirdPartyAccount.getRemainProfileNum());
        return hrThirdPartyAccountDao.updateData(newThirdPartyAccount);
    }
}
