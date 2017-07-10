package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/6/16.
 */

@Service
public class ThirdPartyAccountSynctor {

    static Logger logger = LoggerFactory.getLogger(ThirdPartyAccountSynctor.class);

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);


    static ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();

    static List<String> mails = new ArrayList<>();

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

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;


    /**
     * 第三方帐号异步绑定和刷新的实现
     */
    class ThirdPartyAccountSyncTask implements Runnable {

        HrThirdPartyAccountDO hrThirdPartyAccount;
        Map<String, String> extras;

        int syncType; //绑定0,刷新1

        ThirdPartyAccountSyncTask(int syncType, HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) {
            this.syncType = syncType;
            this.hrThirdPartyAccount = hrThirdPartyAccount;
            this.extras = extras;
        }

        @Override
        public void run() {
            try {
                HrThirdPartyAccountDO result = syncType == 0 ? chaosService.binding(hrThirdPartyAccount, extras) : chaosService.synchronization(hrThirdPartyAccount, extras);
                //刷新成功

                //更新到数据库
                result.setBinding(Short.valueOf("1"));
                result.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                result.setSyncTime(result.getUpdateTime());
                logger.info("Chaos回传解析成功:{}:{}", syncType, JSON.toJSONString(hrThirdPartyAccount));
                updateThirdPartyAccount(result, syncType, null);
            } catch (BIZException e) {
                if (e.getCode() == 1) {
                    //帐号密码错误，将状态改为4
                    hrThirdPartyAccount.setBinding(Short.valueOf("4"));
                    updateThirdPartyAccount(hrThirdPartyAccount, syncType, e.getMessage());
                } else {
                    //Chaos那边的其它异常，发送邮件
                    hrThirdPartyAccount.setBinding(Short.valueOf(syncType == 0 ? "6" : "7"));
                    updateThirdPartyAccount(hrThirdPartyAccount, syncType, "Chaos异常:" + e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                //系统的异常
                hrThirdPartyAccount.setBinding(Short.valueOf(syncType == 0 ? "6" : "7"));
                updateThirdPartyAccount(hrThirdPartyAccount, syncType, "系统异常：" + e.getMessage() == null ? "" : e.getMessage());
            }
        }
    }

    //发送同步失败的邮件
    private void sendFailureMail(int syncType, HrThirdPartyAccountDO thirdPartyAccount, String message) {
        logger.info("发送同步或刷新错误的邮件:syncType{}:thirdPartyAccount:{}:message:{}", syncType, JSON.toJSONString(thirdPartyAccount), message);

        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails);

            ChannelType channelType = ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【第三方帐号").append(syncType == 0 ? "绑定" : "刷新").append("失败】");
            titleBuilder.append(":【").append(channelType.getAlias()).append("】");
            titleBuilder.append(":【").append(thirdPartyAccount.getId()).append("】");

            String br = "<br/>";

            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("【第三方帐号ID】：").append(thirdPartyAccount.getId()).append(br);
            messageBuilder.append("【帐号名】：").append(thirdPartyAccount.getUsername()).append(br);
            if (StringUtils.isNotNullOrEmpty(thirdPartyAccount.getMembername())) {
                messageBuilder.append("【会员名】：").append(thirdPartyAccount.getMembername()).append(br);
            }
            if (StringUtils.isNotNullOrEmpty(message)) {
                messageBuilder.append("【失败信息】:").append(br);
                messageBuilder.append(message).append(br);
            }

            emailBuilder.setSubject(titleBuilder.toString());
            emailBuilder.setContent(messageBuilder.toString());
            Email email = emailBuilder.build();
            email.send(new TransportListener() {
                int i = 3;//重试三次邮件

                @Override
                public void messageDelivered(TransportEvent e) {
                    logger.info("email send messageDelivered");
                }

                @Override
                public void messageNotDelivered(TransportEvent e) {
                    if (i > 0) {
                        logger.info("email send messageNotDelivered retry {}", i);
                        email.send(this);
                        i--;
                    } else {
                        logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
                    }
                }

                @Override
                public void messagePartiallyDelivered(TransportEvent e) {
                    if (i > 0) {
                        logger.info("email send messagePartiallyDelivered retry {}", i);
                        email.send(this);
                        i--;
                    } else {
                        logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            logger.error("发送绑定失败的邮件发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 绑定第三方帐号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @param sync
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO bindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean sync) throws Exception {
        return sync ? syncWithBindThirdPartyAccount(hrId, thirdPartyAccount, extras) : asyncWithBindThirdPartyAccount(hrId, thirdPartyAccount, extras);
    }

    /**
     * 使用异步的方式去同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras) throws Exception {
        //先保存信息到数据库,状态为2绑定中
        thirdPartyAccount.setBinding(Short.valueOf("2"));
        if (thirdPartyAccount.getId() > 0) {
            int updateResult = updateThirdPartyAccount(thirdPartyAccount);
            if (updateResult < 1) {
                throw new BIZException(-1, "无法保存数据，请重试");
            }
        } else {
            thirdPartyAccount = hrThirdPartyAccountDao.addThirdPartyAccount(hrId, thirdPartyAccount);
        }
        //开启线程后台取处理第三方账号同步
        new Thread(new ThirdPartyAccountSyncTask(0, thirdPartyAccount, extras)).start();
        return thirdPartyAccount;
    }


    /**
     * 使用同步的方式同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras) throws Exception {
        //先绑定
        HrThirdPartyAccountDO bindResult = chaosService.binding(thirdPartyAccount, extras);
        //绑定成功之后添加到数据库
        if (thirdPartyAccount.getId() > 0) {
            bindResult.setId(thirdPartyAccount.getId());
            updateThirdPartyAccount(thirdPartyAccount);
        } else {
            bindResult = hrThirdPartyAccountDao.addThirdPartyAccount(hrId, bindResult);
        }
        return bindResult;
    }


    /**
     * 同步第三方帐号的可发布数等
     *
     * @param thirdPartyAccount
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO syncThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean sync) throws Exception {
        return sync ? syncWithSyncThirdPartyAccount(thirdPartyAccount, extras) : asyncWithSyncThirdPartyAccount(thirdPartyAccount, extras);
    }

    /**
     * 异步的方式
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        //先更新数据库的状态为刷新中3
        hrThirdPartyAccount.setBinding(Short.valueOf("3"));
        int updateResult = updateThirdPartyAccount(hrThirdPartyAccount);
        if (updateResult < 1) {
            //更新失败
            throw new BIZException(-1, "系统异常,请重试");
        }

        new Thread(new ThirdPartyAccountSyncTask(1, hrThirdPartyAccount, extras)).start();

        return hrThirdPartyAccount;
    }

    /**
     * 同步的方式
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        HrThirdPartyAccountDO syncResult = chaosService.synchronization(hrThirdPartyAccount, extras);
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
    private void updateThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, int syncType, String emailMsg) {
        int updateResult = updateThirdPartyAccount(hrThirdPartyAccount);

        if (updateResult < 1) {
            logger.error("更新第三方账号失败:{}", JSON.toJSONString(hrThirdPartyAccount));
        }

        if (hrThirdPartyAccount.getBinding() == 1) {
            if (updateResult < 1) {
                logger.warn("Chaos回传解析成功 更改数据信息失败:{}:更改状态:{}", syncType, updateResult);
                sendFailureMail(syncType, hrThirdPartyAccount, (syncType == 0 ? "同步" : "刷新") + "数据无法保存到数据库");
            }
        } else if (hrThirdPartyAccount.getBinding() == 4) {
            logger.info("账号密码错误");
            if (updateResult < 1) {
                //更新失败，发送邮件
                sendFailureMail(syncType, hrThirdPartyAccount, "密码错误，但无法更新到数据库");
            }
        } else if (hrThirdPartyAccount.getBinding() == 6 || hrThirdPartyAccount.getBinding() == 7) {
            if (updateResult > 0) {
                sendFailureMail(syncType, hrThirdPartyAccount, emailMsg);
            } else {
                //程序错误，并且状态无法更新到数据库
                sendFailureMail(syncType, hrThirdPartyAccount, emailMsg + ":数据库状态无法更改");
            }
        }

    }


    private int updateThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount) {
        return hrThirdPartyAccountDao.updateData(hrThirdPartyAccount);
    }
}
